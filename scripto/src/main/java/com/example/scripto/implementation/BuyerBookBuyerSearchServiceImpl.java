package com.example.scripto.implementation;

import com.example.scripto.entity.BookListing;
import com.example.scripto.repository.BookListingRepo;
import com.example.scripto.service.IBuyerSearchBooks;
import jakarta.validation.constraints.Email;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyerBookBuyerSearchServiceImpl implements IBuyerSearchBooks {

    @Autowired
    private BookListingRepo bookListingRepo;



    // for getting the all search book
    @Override
    public ResponseEntity<List<BookListing>> searchBooks(String keyword) {
         try {
             List<BookListing> allBooks =  bookListingRepo.searchBooks(keyword);
             return new ResponseEntity<>(allBooks, HttpStatus.OK);
         }catch (Exception e){
             e.printStackTrace();
             return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
         }
    }




    // for getting suggestions of book name
    @Override
    public ResponseEntity<List<String>> getBookNameSuggestions(String keyword) {
       try {
           List<String> suggestBookNames =  bookListingRepo.suggestBookNames(keyword);
           return new ResponseEntity<>(suggestBookNames,HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
       }
    }





     // for getting suggestions of author name
    @Override
    public ResponseEntity<List<String>> getAuthorSuggestions(String keyword) {
        try{
            List<String> suggestAuthorNames = bookListingRepo.suggestAuthorNames(keyword);
            return new ResponseEntity<>(suggestAuthorNames,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }
}
