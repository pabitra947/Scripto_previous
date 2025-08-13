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
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private BuyerAddress address;  // Chosen delivery address

    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private LocalDateTime orderDate = LocalDateTime.now();

    // Convenience: derive a simple order-level state (optional)
    public boolean allItemsConfirmed() {
        return items != null && items.stream().allMatch(i -> i.getStatus() == OrderItemStatus.CONFIRMED
                || i.getStatus() == OrderItemStatus.SHIPPED
                || i.getStatus() == OrderItemStatus.DELIVERED);
    }
}

