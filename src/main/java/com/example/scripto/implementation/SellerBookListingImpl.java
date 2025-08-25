package com.example.scripto.implementation;

import com.example.scripto.dto.*;

import com.example.scripto.entity.BookListing;
import com.example.scripto.entity.User;
import com.example.scripto.repository.SellerBookListingRepo;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.response.seller.book.SellerBookResponseByAuthorName;
import com.example.scripto.response.seller.book.SellerBookResponseByBookName;
import com.example.scripto.response.seller.book.SellerBookResponse;
import com.example.scripto.response.seller.book.SellerBookResponseOnPrice;
import com.example.scripto.service.ISellerBookListing;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerBookListingImpl implements ISellerBookListing {
//    private BookListing bookinformation = new BookListing();
    private ModelMapper modelMapper = new ModelMapper();


    @Autowired
    private SellerBookListingRepo sellerBookListingRepo;

    @Autowired
    private UserRepository userRepository;


    //add new Book
    @Override
    public ResponseEntity<BookListing> addNewBook(SellerBookDto information) {
        try {
            BookListing existingBook = sellerBookListingRepo.findByBookNameAndAuthorName(
                    information.getBookName(), information.getAuthorName());

            if (existingBook != null) {
                throw new RuntimeException("Book is already present.");
            }

            //Get logged-in user's email/username
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            //Fetch user from DB
            User seller = userRepository.findByEmail(email);

            if(seller == null){
                throw new RuntimeException("user not found..");
            }


            //Map & set seller
            BookListing book = modelMapper.map(information, BookListing.class);
            book.setSoldQuantity(0);
            book.setSeller(seller);
            BookListing saved = sellerBookListingRepo.save(book);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    //Used to update the book price and the quantity
    @Override
    public ResponseEntity<BookListing> updateBookDetails(Long bookId, SellerEditBookDto updateDto) {
        try {
            // Step 1: Get the authenticated user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName();

            // Step 2: Fetch the book by ID
            Optional<BookListing> optionalBook = sellerBookListingRepo.findById(bookId);
            if (optionalBook.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            BookListing book = optionalBook.get();

            // Step 3: Check if the logged-in user is the book's seller
            if (!book.getSeller().getEmail().equals(loggedInEmail)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Not authorized
            }

            // Step 4: Update price and quantity if provided
            if (updateDto.getPrice() != null) {
                book.setPrice(updateDto.getPrice());
            }

            if (updateDto.getTotalQuantity() != null) {
                book.setTotalQuantity(updateDto.getTotalQuantity());
            }

            book.setUpdatedDateAndTime(LocalDateTime.now());

            BookListing updated = sellerBookListingRepo.save(book);
            return new ResponseEntity<>(updated, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    //Used to delete the book from DB
    @Override
    @Transactional
    public ResponseEntity<?> deleteBookById(long id){
        try {
            // Step 1: Get the authenticated user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName();

            Optional<BookListing> book = sellerBookListingRepo.findById(id);
            if (book.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            BookListing bookListing = book.get();

            if(bookListing.getSeller().getEmail().equals(loggedInEmail)){
                sellerBookListingRepo.deleteById(id);
                return new ResponseEntity<>("The book is deleted", HttpStatus.OK);
            }
            else{
                throw new RuntimeException("This book id is not present.");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Somethins went wrong", HttpStatus.UNAUTHORIZED);
        }
    }



    //Used to find all the unique book
    @Override
    public ResponseEntity<List<SellerBookResponse>> findAllUniqueBook(){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            //Fetch only books listed by this user (seller)
            List<BookListing> books = sellerBookListingRepo.findAllBooksBySellerEmail(loggedInEmail);

            //Map entities to response DTOs
            List<SellerBookResponse> response = books.stream().map(
                    book -> new SellerBookResponse(
                            book.getBookId(),
                            book.getBookName(),
                            book.getAuthorName(),
                            book.getPrice(),
                            book.getTotalQuantity(),
                            book.getSoldQuantity(),
                            book.getAvailableQuantity(),
                            book.getBookDetails(),
                            book.getImageUrl(),
                            book.getCreatedDateAndTime()
                    )
            ).collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }




    //Used to find the book based on the book name
    @Override
    public ResponseEntity<List<SellerBookResponseByBookName>> findBookByBookName(String book){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByBookName = sellerBookListingRepo.findBookByBookName(loggedInEmail, book);

            if(allBookByBookName != null){
                List<SellerBookResponseByBookName> books = allBookByBookName.stream().map(
                        booked -> new SellerBookResponseByBookName(
                                booked.getBookId(),
                                booked.getAuthorName(),
                                booked.getPrice(),
                                booked.getTotalQuantity(),
                                booked.getSoldQuantity(),
                                booked.getAvailableQuantity(),  // custom calculated field from getter
                                booked.getBookDetails(),
                                booked.getImageUrl(),
                                booked.getCreatedDateAndTime()
                        )
                ).collect(Collectors.toList());

                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book based on the Author name
    @Override
    public ResponseEntity<List<SellerBookResponseByAuthorName>> findBookByAuthorName(String author){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByAuthorName = sellerBookListingRepo.findBookByAuthorName(loggedInEmail, author);

            if(allBookByAuthorName != null){
                List<SellerBookResponseByAuthorName> books = allBookByAuthorName.stream().map(
                        book -> new SellerBookResponseByAuthorName(
                                book.getBookId(),
                                book.getBookName(),
                                book.getPrice(),
                                book.getTotalQuantity(),
                                book.getSoldQuantity(),
                                book.getAvailableQuantity(),  // custom calculated field from getter
                                book.getBookDetails(),
                                book.getImageUrl(),
                                book.getCreatedDateAndTime()
                        )
                ).collect(Collectors.toList());

                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book on a particular price point
    @Override
    public ResponseEntity<List<SellerBookResponseOnPrice>> findBookByCheaperThanThePrice(Double price){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByPrice = sellerBookListingRepo.findBooksCheaperThanThePrice(loggedInEmail, price);

            if (!allBookByPrice.isEmpty()) {
                List<SellerBookResponseOnPrice> books = allBookByPrice.stream().map(
                        book -> new SellerBookResponseOnPrice(
                                book.getBookId(),
                                book.getBookName(),
                                book.getAuthorName(),
                                book.getPrice(),
                                book.getTotalQuantity(),
                                book.getSoldQuantity(),
                                book.getAvailableQuantity(),  // custom calculated field from getter
                                book.getBookDetails(),
                                book.getImageUrl(),
                                book.getCreatedDateAndTime()
                        )
                ).collect(Collectors.toList());
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book on a particular price range 
    @Override
    public ResponseEntity<List<SellerBookResponseOnPrice>> findBookByPriceRange(Double min, Double max){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByPriceRange = sellerBookListingRepo.findBySellerEmailAndPriceBetween(loggedInEmail, min, max);

            if (!allBookByPriceRange.isEmpty()) {
                List<SellerBookResponseOnPrice> books = allBookByPriceRange.stream().map(
                        book -> new SellerBookResponseOnPrice(
                                book.getBookId(),
                                book.getBookName(),
                                book.getAuthorName(),
                                book.getPrice(),
                                book.getTotalQuantity(),
                                book.getSoldQuantity(),
                                book.getAvailableQuantity(),  // custom calculated field from getter
                                book.getBookDetails(),
                                book.getImageUrl(),
                                book.getCreatedDateAndTime()
                        )
                ).collect(Collectors.toList());
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book Higher then the given price
    @Override
    public ResponseEntity<List<SellerBookResponseOnPrice>> findBookByHigherThenThePrice(Double price){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByPrice = sellerBookListingRepo.findBooksCostlierThanThePrice(loggedInEmail, price);

            if (!allBookByPrice.isEmpty()) {
                List<SellerBookResponseOnPrice> books = allBookByPrice.stream().map(
                        book -> new SellerBookResponseOnPrice(
                                book.getBookId(),
                                book.getBookName(),
                                book.getAuthorName(),
                                book.getPrice(),
                                book.getTotalQuantity(),
                                book.getSoldQuantity(),
                                book.getAvailableQuantity(),  // custom calculated field from getter
                                book.getBookDetails(),
                                book.getImageUrl(),
                                book.getCreatedDateAndTime()
                        )
                ).collect(Collectors.toList());
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
