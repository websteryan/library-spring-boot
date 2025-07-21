package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    
    boolean existsByIsbn(String isbn);
    
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:isbn IS NULL OR b.isbn = :isbn) AND " +
           "(:categoryId IS NULL OR b.category.id = :categoryId)")
    Page<Book> findBooksWithFilters(@Param("title") String title,
                                   @Param("author") String author,
                                   @Param("isbn") String isbn,
                                   @Param("categoryId") Long categoryId,
                                   Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0")
    Page<Book> findAvailableBooks(Pageable pageable);
}
