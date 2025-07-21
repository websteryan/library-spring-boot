package com.library.controller;

import com.library.dto.request.BookRequest;
import com.library.dto.response.ApiResponse;
import com.library.entity.Book;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Books", description = "Book management APIs")
public class BookController {
    
    private final BookService bookService;
    
    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<ApiResponse<Page<Book>>> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search books")
    public ResponseEntity<ApiResponse<Page<Book>>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable) {
        Page<Book> books = bookService.searchBooks(title, author, isbn, categoryId, pageable);
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/available")
    @Operation(summary = "Get available books")
    public ResponseEntity<ApiResponse<Page<Book>>> getAvailableBooks(Pageable pageable) {
        Page<Book> books = bookService.getAvailableBooks(pageable);
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success(book));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @Operation(summary = "Create a new book")
    public ResponseEntity<ApiResponse<Book>> createBook(@Valid @RequestBody BookRequest request) {
        Book book = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book created successfully", book));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @Operation(summary = "Update book")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        Book book = bookService.updateBook(id, request);
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", book));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete book")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }
}
