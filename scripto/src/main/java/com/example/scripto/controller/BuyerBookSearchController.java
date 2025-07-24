package com.example.scripto.controller;

import com.example.scripto.implementation.BuyerBookSearchServiceImpl;
import com.example.scripto.response.buyer.BuyerBookResponse;
import com.example.scripto.response.buyer.BuyerBookResponseByAuthorName;
import com.example.scripto.response.buyer.BuyerBookResponseByBookName;
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


    // Search books by name OR author
    @GetMapping("/search")
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
}
