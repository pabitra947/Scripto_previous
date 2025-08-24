package com.example.scripto.repository;

import com.example.scripto.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerBookListingRepo extends JpaRepository<BookListing, Long> {

    BookListing findByBookNameAndAuthorName(String bookName, String authorName);


    @Query("SELECT b FROM BookListing b WHERE b.seller.email = :email")
    List<BookListing> findAllBooksBySellerEmail(@Param("email") String email); //use JPQL



    @Query("SELECT b FROM BookListing as b WHERE b.bookName = :bookName AND b.seller.email = :email")
    List<BookListing> findBookByBookName(@Param("email") String email,
                                         @Param("bookName") String bookName); //use JPQL


    @Query("SELECT b FROM BookListing b WHERE b.seller.email = :email AND LOWER(b.authorName) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<BookListing> findBookByAuthorName(@Param("email") String email,
                                           @Param("authorName") String authorName);//use JPQL


    @Query(value = "SELECT b FROM BookListing b WHERE b.seller.email = :email AND b.price <= :price")
    List<BookListing> findBooksCheaperThanThePrice(@Param("email") String email,
                                                   @Param("price") Double price);


    @Query("SELECT b FROM BookListing b WHERE b.seller.email = :email AND b.price BETWEEN :min AND :max")
    List<BookListing> findBySellerEmailAndPriceBetween(@Param("email") String email,
                                                       @Param("min") Double min,
                                                       @Param("max") Double max);


    @Query(value = "SELECT b FROM BookListing b WHERE b.seller.email = :email AND b.price >= :price")
    List<BookListing> findBooksCostlierThanThePrice(@Param("email") String email, @Param("price") Double price);

}







//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE book_listing SET price = :price, posted_at = Now() WHERE id = :id", nativeQuery = true)
//    int updateBookPriceById(@Param("id") long id, @Param("price") double price);