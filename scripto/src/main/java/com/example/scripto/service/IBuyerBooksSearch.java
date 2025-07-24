package com.example.scripto.service;

import com.example.scripto.response.buyer.BuyerBookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBuyerBooksSearch {
    ResponseEntity<List<BuyerBookResponse>> searchBooks(String keyword);

    ResponseEntity<List<BuyerBookResponse>> getBookByBookName(String bookName);

    ResponseEntity<List<BuyerBookResponse>> getBookByAuthorName(String AuthorName);
}
