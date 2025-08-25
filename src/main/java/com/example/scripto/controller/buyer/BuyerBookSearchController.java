package com.example.scripto.controller.buyer;

import com.example.scripto.implementation.BuyerBookSearchServiceImpl;
import com.example.scripto.response.buyer.book.BuyerBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyer-view")
public class BuyerBookSearchController {

    @Autowired
    private BuyerBookSearchServiceImpl bookSearchService;


    //all is here
    @GetMapping("/search")
    public ResponseEntity<List<BuyerBookResponse>> searchBooks(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        return bookSearchService.searchBooks(bookName, authorName, minPrice, maxPrice);
    }
}















/*
// Search books by name OR author
@GetMapping("/search-book")
public ResponseEntity<List<BuyerBookResponse>> searchBooks(@RequestParam String keyword) {
    return bookSearchService.searchBooks(keyword);
}



// Suggest book names
@GetMapping("/suggest/book-names")
public ResponseEntity<List<BuyerBookResponse>> getBookByBookNames(@RequestParam String bookName) {
    return bookSearchService.getBookByBookName(bookName);
}



// Suggest author names
@GetMapping("/suggest/author-name")
public ResponseEntity<List<BuyerBookResponse>> getBookByAuthorNames(@RequestParam String authorName) {
    return bookSearchService.getBookByAuthorName(authorName);
}



//After search filter based on the price range (between the min and and max price)
@GetMapping("/books-by-price-range")
public ResponseEntity<List<BuyerBookResponse>> getBookBetweenThePriceRange(@RequestParam String keyword, @RequestParam Double min, @RequestParam Double max){
    return bookSearchService.findBookByPriceRange(keyword, min, max);
}
* */