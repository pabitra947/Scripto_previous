package com.example.scripto.repository;

import com.example.scripto.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID> {

}
