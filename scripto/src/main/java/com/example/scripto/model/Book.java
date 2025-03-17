package com.example.scripto.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    private String title;


    private String author;
    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;


    @Column(columnDefinition = "TEXT")
    private String description;


    private String imageUrl;


    @Enumerated(EnumType.STRING)
    private Status status;


    private LocalDateTime createdAt;
}
