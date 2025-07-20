package com.example.scripto.implementation;

import com.example.scripto.dto.*;

import com.example.scripto.entity.BookListing;
import com.example.scripto.repository.BookListingRepo;
import com.example.scripto.service.IBookListing;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookListingImpl implements IBookListing {
    private BookListing bookinformation = new BookListing();
    private ModelMapper modelMapper = new ModelMapper();


    @Autowired
    private BookListingRepo bookListingRepo;


    //add new Book
    @Override
    public ResponseEntity<BookListing> addNewBook(BookDto information){
        try {
            BookListing book=bookListingRepo.findByBookNameAndAuthorName(information.getBookName(), information.getAuthorName());

            if(book == null)
            {
                bookinformation = modelMapper.map(information, BookListing.class);
                bookinformation.setSoldQuantity(0);
                BookListing newBook = bookListingRepo.save(bookinformation);
                return new ResponseEntity<>(newBook, HttpStatus.CREATED);
            }
            else{
                throw new RuntimeException("Book is already present..");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to update the book price and the quantity
    @Override
    public ResponseEntity<BookListing> updateBookDetails(Long bookId, EditBookDto updateDto) {
        try {
            //Find the book using id from the DB
            Optional<BookListing> optionalBook = bookListingRepo.findById(bookId);

            //check from book existence
            if (optionalBook.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            BookListing book = optionalBook.get();

            if (updateDto.getPrice() != null) {
                book.setPrice(updateDto.getPrice());
            }

            if (updateDto.getTotalQuantity() != null) {
                book.setTotalQuantity(updateDto.getTotalQuantity());
            }

            book.setUpdatedDateAndTime(LocalDateTime.now());

            BookListing updated = bookListingRepo.save(book);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //Used to delete the book from DB
    @Override
    @Transactional
    public ResponseEntity<?> deleteBookById(long id){
        try {
            Optional<BookListing> book = bookListingRepo.findById(id);
            if(book.isPresent()){
                bookListingRepo.deleteById(id);
                return new ResponseEntity<>("The book is deleted", HttpStatus.OK);
            }
            else{
                throw new RuntimeException("This book id is not present.");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Somethins went wrong", HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find all the unique book
    @Override
    public ResponseEntity<List<BookResponseDto>> findAllUniqueBook(){
        try {
            List<BookListing> books = bookListingRepo.findAllUniqueBook();

            List<BookResponseDto> response = books.stream().map(
                    book -> new BookResponseDto(
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

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }



    //Used to find the book based on the book name
    @Override
    public ResponseEntity<List<BookResponseByBookNameDto>> findBookByBookName(String book){
        try {
            List<BookListing> allBookByBookName = bookListingRepo.findBookByBookName(book);

            if(allBookByBookName != null){
                List<BookResponseByBookNameDto> books = allBookByBookName.stream().map(
                        booked -> new BookResponseByBookNameDto(
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
}
