package com.example.scripto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookListing implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;


    @NonNull
    private String bookName;


    private int totalQuantity;  // Total copies listed by admin
    private int soldQuantity;   // Copies sold so far

    @NonNull
    private Double price;

    @NonNull
    private String authorName;


    @Column(columnDefinition = "TEXT")
    private String bookDetails;

    private LocalDateTime createdDateAndTime = LocalDateTime.now();
    private LocalDateTime updatedDateAndTime = LocalDateTime.now();

    @NonNull
    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "bookListings"})
    private User seller;


    // 🟢 Optional: calculates how many are still available
    public int getAvailableQuantity() {
        return totalQuantity - soldQuantity;
    }


    // Optional if you want to auto update postedAt on update:
    @PrePersist
    public void prePersist() {
        this.updatedDateAndTime = LocalDateTime.now();
    }
}