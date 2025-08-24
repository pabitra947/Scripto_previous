package com.example.scripto.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private BookListing book;

    private int quantity;
    private Double price; // snapshot price per item

    @Enumerated(EnumType.STRING)
    private OrderItemStatus status = OrderItemStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
}

