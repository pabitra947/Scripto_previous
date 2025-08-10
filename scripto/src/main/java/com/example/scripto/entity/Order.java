package com.example.scripto.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    private LocalDateTime orderDate = LocalDateTime.now();

    private Double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    // Convenience: derive a simple order-level state (optional)
    public boolean allItemsConfirmed() {
        return items != null && items.stream().allMatch(i -> i.getStatus() == OrderItemStatus.CONFIRMED
                || i.getStatus() == OrderItemStatus.SHIPPED
                || i.getStatus() == OrderItemStatus.DELIVERED);
    }
}

