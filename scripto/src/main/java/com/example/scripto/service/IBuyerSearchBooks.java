package com.example.scripto.service;

import com.example.scripto.entity.BookListing;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBuyerSearchBooks {
    ResponseEntity<List<BookListing>> searchBooks(String keyword);

    ResponseEntity<List<String>> getBookNameSuggestions(String keyword);

    ResponseEntity<List<String>> getAuthorSuggestions(String keyword);
}
