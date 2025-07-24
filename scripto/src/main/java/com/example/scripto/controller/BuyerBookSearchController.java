package com.example.scripto.controller;

import com.example.scripto.entity.BookListing;
import com.example.scripto.implementation.BuyerBookBuyerSearchServiceImpl;
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
    private BuyerBookBuyerSearchServiceImpl bookSearchService;

    // Search books by name OR author
    @GetMapping("/search")
    public ResponseEntity<List<BookListing>> searchBooks(@RequestParam String keyword) {
        return bookSearchService.searchBooks(keyword);
    }



    // Suggest book names
    @GetMapping("/suggest/book-names")
    public ResponseEntity<List<String>> suggestBookNames(@RequestParam String keyword) {
        return bookSearchService.getBookNameSuggestions(keyword);
    }



    // Suggest author names
    @GetMapping("/suggest/authors")
    public ResponseEntity<List<String>> suggestAuthorNames(@RequestParam String keyword) {
        return bookSearchService.getAuthorSuggestions(keyword);
    }
}
