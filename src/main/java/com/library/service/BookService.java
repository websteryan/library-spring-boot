package com.library.service;

import com.library.dto.request.BookRequest;
import com.library.entity.Book;
import com.library.entity.Category;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    public Page<Book> searchBooks(String title, String author, String isbn, Long categoryId, Pageable pageable) {
        return bookRepository.findBooksWithFilters(title, author, isbn, categoryId, pageable);
    }
    
    public Page<Book> getAvailableBooks(Pageable pageable) {
        return bookRepository.findAvailableBooks(pageable);
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }
    
    @Transactional
    public Book createBook(BookRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
        
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .description(request.getDescription())
                .totalCopies(request.getTotalCopies())
                .availableCopies(request.getTotalCopies())
                .category(category)
                .build();
        
        return bookRepository.save(book);
    }
    
    @Transactional
    public Book updateBook(Long id, BookRequest request) {
        Book book = getBookById(id);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
        
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setTotalCopies(request.getTotalCopies());
        book.setCategory(category);
        
        return bookRepository.save(book);
    }
    
    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}
