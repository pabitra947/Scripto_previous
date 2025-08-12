package com.example.scripto.implementation;

import com.example.scripto.entity.BookListing;
import com.example.scripto.repository.BuyerBookRepo;
import com.example.scripto.response.buyer.book.BuyerBookResponse;
import com.example.scripto.service.IBuyerBooksSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyerBookSearchServiceImpl implements IBuyerBooksSearch {

    @Autowired
    private BuyerBookRepo buyerBookRepo;




    //All feature is done in one here
    @Override
    public ResponseEntity<List<BuyerBookResponse>> searchBooks(
            String bookName, String authorName, Double minPrice, Double maxPrice) {
        try {
            // Normalize input
            String normalizedBook = (bookName != null && !bookName.isBlank()) ? bookName : null;
            String normalizedAuthor = (authorName != null && !authorName.isBlank()) ? authorName : null;

            List<BookListing> listings = buyerBookRepo.searchByBookAndAuthorAndPrice(
                    normalizedBook, normalizedAuthor, minPrice, maxPrice
            );

            List<BuyerBookResponse> response = listings.stream()
                    .map(BuyerBookResponse::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}



















/*
*

    // for getting the all search book
    @Override
    public ResponseEntity<List<BuyerBookResponse>> searchBooks(String keyword) {
        try {
            List<BookListing> availableBooks = buyerBookListingRepo.searchBooks(keyword);

            if (availableBooks.isEmpty()) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }

            List<BuyerBookResponse> response = availableBooks.stream().map(BuyerBookResponse::new).collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }





    // for getting suggestions of book name
    @Override
    public ResponseEntity<List<BuyerBookResponse>> getBookByBookName(String bookName) {
       try {
            List<BookListing> availableBooks =  buyerBookListingRepo.getBookByBookNames(bookName);

           if (availableBooks.isEmpty()) {
               return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
           }

           List<BuyerBookResponse> response = availableBooks.stream().map(BuyerBookResponse::new).collect(Collectors.toList());
           return new ResponseEntity<>(response,HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
       }
    }





     // for getting suggestions of author name
    @Override
    public ResponseEntity<List<BuyerBookResponse>> getBookByAuthorName(String authorName) {
        try{
            List<BookListing> availableBooks = buyerBookListingRepo.suggestAuthorNames(authorName);

            if (availableBooks.isEmpty()) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }

            List<BuyerBookResponse> response = availableBooks.stream().map(BuyerBookResponse::new).collect(Collectors.toList());

            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }




    //get user by between the price range
    @Override
    public ResponseEntity<List<BuyerBookResponse>> findBookByPriceRange(String keyword, Double minPrice, Double maxPrice){
        try {
            List<BookListing> searchResults = buyerBookListingRepo.searchBooks(keyword);

            // Filter by price range
            List<BuyerBookResponse> filtered = searchResults.stream()
                    .filter(book -> book.getPrice() >= minPrice && book.getPrice() <= maxPrice)
                    .map(BuyerBookResponse::new) // Convert to response DTO
                    .collect(Collectors.toList());

            return new ResponseEntity<>(filtered, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }



* */