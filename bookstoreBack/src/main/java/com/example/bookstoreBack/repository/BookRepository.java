package com.example.bookstoreBack.repository;

import com.example.bookstoreBack.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM Book b WHERE b.publishYear <= :maxYear")
    List<Book> findOutdatedBooks(@Param("maxYear") Integer maxYear);

    @Query("SELECT b FROM Book b WHERE b.createdDate <= :cutoffDate")
    List<Book> findBooksOlderThan(@Param("cutoffDate") LocalDate cutoffDate);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);

    @Query("SELECT b FROM Book b WHERE TYPE(b) = :bookType")
    List<Book> findByBookType(@Param("bookType") Class<? extends Book> bookType);
}
