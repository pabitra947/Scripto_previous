package com.example.scripto.repository;

import com.example.scripto.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyerBookRepo extends JpaRepository<BookListing, Long> {


    //All task in one here
    @Query("SELECT b FROM BookListing b " +
            "WHERE (b.totalQuantity - b.soldQuantity) > 0 " +  // Only available
            "AND (:bookName IS NULL OR LOWER(b.bookName) LIKE LOWER(CONCAT('%', :bookName, '%'))) " +
            "AND (:authorName IS NULL OR LOWER(b.authorName) LIKE LOWER(CONCAT('%', :authorName, '%'))) " +
            "AND (:minPrice IS NULL OR b.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR b.price <= :maxPrice)")
    List<BookListing> searchByBookAndAuthorAndPrice(
            @Param("bookName") String bookName,
            @Param("authorName") String authorName,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);

}












/*
*
    // Search by book name OR author name
    @Query("SELECT b FROM BookListing b " +
            "WHERE (LOWER(b.bookName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(b.authorName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (b.totalQuantity - b.soldQuantity) > 0")
    List<BookListing> searchBooks(@Param("keyword") String keyword);



    // For suggestions (only book names)
    @Query("SELECT b FROM BookListing b WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (b.totalQuantity - b.soldQuantity) > 0")
    List<BookListing> getBookByBookNames(@Param("keyword") String keyword);

    @Query("SELECT b FROM BookListing b WHERE LOWER(b.authorName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "AND (b.totalQuantity - b.soldQuantity) > 0")
    List<BookListing> suggestAuthorNames(@Param("keyword") String keyword);
* */