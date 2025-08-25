package com.example.scripto.service;

import com.example.scripto.response.buyer.book.BuyerBookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBuyerBooksSearch {

    //All is here
    ResponseEntity<List<BuyerBookResponse>> searchBooks(
            String bookName, String authorName, Double minPrice, Double maxPrice);
}











/*
* ResponseEntity<List<BuyerBookResponse>> searchBooks(String keyword);

    ResponseEntity<List<BuyerBookResponse>> getBookByBookName(String bookName);

    ResponseEntity<List<BuyerBookResponse>> getBookByAuthorName(String authorName);

    ResponseEntity<List<BuyerBookResponse>> findBookByPriceRange(String keyword, Double min, Double max);
* */