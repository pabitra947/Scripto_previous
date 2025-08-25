package com.example.scripto.repository;

import com.example.scripto.entity.User;
import com.example.scripto.entity.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyerWishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    boolean existsByUserAndBook_BookId(User user, Long bookId);  // Correct


    Optional<Wishlist> findByUserAndBook_BookId(User user, Long bookId);



}








// Get all wishlist items of a user
//    @Query("SELECT w FROM Wishlist w WHERE w.user.userId = :userId")
//    List<Wishlist> findAllByUserId(@Param("userId") Long userId);
//
//    // Check if a specific book is already in a user’s wishlist
//    @Query("SELECT w FROM Wishlist w WHERE w.user.userId = :userId AND w.book.bookId = :bookId")
//    Wishlist findWishlistItem(@Param("userId") Long userId, @Param("bookId") Long bookId);
//
//    //  Delete a wishlist entry directly
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Wishlist w WHERE w.user.userId = :userId AND w.book.bookId = :bookId")
//    void deleteWishlistItem(@Param("userId") Long userId, @Param("bookId") Long bookId);


