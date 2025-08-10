package com.example.scripto.repository;

import com.example.scripto.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByBook_Seller_Email(String sellerEmail);
}
