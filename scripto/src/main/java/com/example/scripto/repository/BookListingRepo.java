package com.example.scripto.repository;

import com.example.scripto.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookListingRepo extends JpaRepository<BookListing, Long> {

    BookListing findByBookNameAndAuthorName(String bookName, String authorName);


    @Query(value = "SELECT " +
            "  MIN(book_id) AS book_id, " +
            "  book_name, " +
            "  author_name, " +
            "  MAX(price) AS price, " +
            "  MAX(total_quantity) AS total_quantity, " +
            "  MAX(sold_quantity) AS sold_quantity, " +
            "  MAX(book_details) AS book_details, " +
            "  MAX(image_url) AS image_url, " +
            "  MAX(created_date_and_time) AS created_date_and_time, " +
            "  MAX(updated_date_and_time) AS updated_date_and_time " +
            "FROM bookinfo " +
            "GROUP BY book_name, author_name; ", nativeQuery = true)
    List<BookListing> findAllUniqueBook();


    @Query(value = "SELECT * FROM bookinfo WHERE book_name = :bookName", nativeQuery = true)
    List<BookListing> findBookByBookName(@Param("bookName") String bookName);


    @Query(value = "SELECT * FROM bookinfo b WHERE LOWER(b.author_name) LIKE LOWER(CONCAT('%', :authorName, '%'))", nativeQuery = true)
    List<BookListing> findBookByAuthorName(@Param("authorName") String authorName);


    @Query(value = "SELECT * FROM bookinfo as b WHERE b.price <= :price", nativeQuery = true)
    List<BookListing> findBooksCheaperThanThePrice(@Param("price") Double price);


    List<BookListing> findByPriceBetween(Double min, Double max);


    @Query(value = "SELECT * FROM bookinfo as b WHERE b.price >= :price", nativeQuery = true)
    List<BookListing> findBooksCostlierThanThePrice(@Param("price") Double price);




    // Search by book name OR author name
    @Query("SELECT b FROM BookListing b " +
            "WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(b.authorName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<BookListing> searchBooks(@Param("keyword") String keyword);

    // For suggestions (only book names & author names)
    @Query("SELECT DISTINCT b.bookName FROM BookListing b WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<String> suggestBookNames(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT b.authorName FROM BookListing b WHERE LOWER(b.authorName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<String> suggestAuthorNames(@Param("keyword") String keyword);
}







//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE book_listing SET price = :price, posted_at = Now() WHERE id = :id", nativeQuery = true)
//    int updateBookPriceById(@Param("id") long id, @Param("price") double price);