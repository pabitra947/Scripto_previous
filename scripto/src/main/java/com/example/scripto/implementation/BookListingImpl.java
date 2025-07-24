package com.example.scripto.implementation;

import com.example.scripto.dto.*;

import com.example.scripto.entity.BookListing;
import com.example.scripto.entity.User;
import com.example.scripto.repository.BookListingRepo;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.response.admin.book.BookResponseByAuthorName;
import com.example.scripto.response.admin.book.BookResponseByBookName;
import com.example.scripto.response.admin.book.BookResponse;
import com.example.scripto.response.admin.book.BookResponseOnPrice;
import com.example.scripto.service.IBookListing;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookListingImpl implements IBookListing {
//    private BookListing bookinformation = new BookListing();
    private ModelMapper modelMapper = new ModelMapper();


    @Autowired
    private BookListingRepo bookListingRepo;

    @Autowired
    private UserRepository userRepository;


    //add new Book
    @Override
    public ResponseEntity<BookListing> addNewBook(BookDto information) {
        try {
            BookListing existingBook = bookListingRepo.findByBookNameAndAuthorName(
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
            BookListing saved = bookListingRepo.save(book);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    //Used to update the book price and the quantity
    @Override
    public ResponseEntity<BookListing> updateBookDetails(Long bookId, EditBookDto updateDto) {
        try {
            // Step 1: Get the authenticated user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName();

            // Step 2: Fetch the book by ID
            Optional<BookListing> optionalBook = bookListingRepo.findById(bookId);
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

            BookListing updated = bookListingRepo.save(book);
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

            Optional<BookListing> book = bookListingRepo.findById(id);
            if (book.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            BookListing bookListing = book.get();

            if(bookListing.getSeller().getEmail().equals(loggedInEmail)){
                bookListingRepo.deleteById(id);
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
    public ResponseEntity<List<BookResponse>> findAllUniqueBook(){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            //Fetch only books listed by this user (seller)
            List<BookListing> books = bookListingRepo.findAllBooksBySellerEmail(loggedInEmail);

            //Map entities to response DTOs
            List<BookResponse> response = books.stream().map(
                    book -> new BookResponse(
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
    public ResponseEntity<List<BookResponseByBookName>> findBookByBookName(String book){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByBookName = bookListingRepo.findBookByBookName(loggedInEmail, book);

            if(allBookByBookName != null){
                List<BookResponseByBookName> books = allBookByBookName.stream().map(
                        booked -> new BookResponseByBookName(
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
                throw new RuntimeException("This name book is not present");
            }

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book based on the Author name
    @Override
    public ResponseEntity<List<BookResponseByAuthorName>> findBookByAuthorName(String author){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByAuthorName = bookListingRepo.findBookByAuthorName(loggedInEmail, author);

            if(allBookByAuthorName != null){
                List<BookResponseByAuthorName> books = allBookByAuthorName.stream().map(
                        book -> new BookResponseByAuthorName(
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
                throw new RuntimeException("This author named book is not present.");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book on a particular price point
    @Override
    public ResponseEntity<List<BookResponseOnPrice>> findBookByCheaperThanThePrice(Double price){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByPrice = bookListingRepo.findBooksCheaperThanThePrice(loggedInEmail, price);

            if (!allBookByPrice.isEmpty()) {
                List<BookResponseOnPrice> books = allBookByPrice.stream().map(
                        book -> new BookResponseOnPrice(
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
                throw new RuntimeException("Below this price point no book is available.");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book on a particular price range 
    @Override
    public ResponseEntity<List<BookResponseOnPrice>> findBookByPriceRange(Double min, Double max){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByPriceRange = bookListingRepo.findBySellerEmailAndPriceBetween(loggedInEmail, min, max);

            if (!allBookByPriceRange.isEmpty()) {
                List<BookResponseOnPrice> books = allBookByPriceRange.stream().map(
                        book -> new BookResponseOnPrice(
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
                throw new RuntimeException("between this price point no book is available.");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book Higher then the given price
    @Override
    public ResponseEntity<List<BookResponseOnPrice>> findBookByHigherThenThePrice(Double price){
        try {
            // Get logged-in user's email
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInEmail = authentication.getName(); // Assuming email is used as username

            List<BookListing> allBookByPrice = bookListingRepo.findBooksCostlierThanThePrice(loggedInEmail, price);

            if (!allBookByPrice.isEmpty()) {
                List<BookResponseOnPrice> books = allBookByPrice.stream().map(
                        book -> new BookResponseOnPrice(
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
                throw new RuntimeException("above this price point no book is available.");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
