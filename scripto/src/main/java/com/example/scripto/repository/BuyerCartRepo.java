package com.example.scripto.repository;

import com.example.scripto.entity.CartItem;
import com.example.scripto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyerCartRepo extends JpaRepository<CartItem,Long> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    User findUserById(@Param("userId") Long userId);

    @Query("SELECT c FROM CartItem c WHERE c.buyer.userId = :buyerId")
    List<CartItem> findAllByBuyerId(@Param("buyerId") Long buyerId);

    @Query("SELECT c FROM CartItem c WHERE c.buyer.userId = :buyerId AND c.book.bookId = :bookId")
    CartItem findCartItem(@Param("buyerId") Long buyerId, @Param("bookId") Long bookId);

    void deleteByBuyerUserIdAndBookBookId(Long buyerId, Long bookId);
}

