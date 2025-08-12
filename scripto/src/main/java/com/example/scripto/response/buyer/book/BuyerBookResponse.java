package com.example.scripto.response.buyer.book;

import com.example.scripto.entity.BookListing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerBookResponse {
    private String bookName;
    private String authorName;
    private Double price;
    private int availableQuantity;
    private String bookDetails;
    private String imageUrl;

    public BuyerBookResponse(BookListing book) {
        this.bookName = book.getBookName();
        this.authorName = book.getAuthorName();
        this.price = book.getPrice();
        this.availableQuantity = book.getAvailableQuantity();
        this.bookDetails = book.getBookDetails();
        this.imageUrl = book.getImageUrl();
    }
}
