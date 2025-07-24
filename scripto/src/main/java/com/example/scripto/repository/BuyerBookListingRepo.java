package com.example.scripto.repository;

import com.example.scripto.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyerBookListingRepo extends JpaRepository<BookListing, Long> {

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

//    @Query(value = "SELECT b FROM BookListing b WHERE b.seller.email = :email AND b.price >= :price")
//    List<BookListing> findBooksCostlierThanThePrice(@Param("email") String email, @Param("price") Double price);
}
