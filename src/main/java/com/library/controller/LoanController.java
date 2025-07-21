package com.library.controller;

import com.library.dto.response.ApiResponse;
import com.library.entity.Loan;
import com.library.enums.LoanStatus;
import com.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Loans", description = "Loan management APIs")
public class LoanController {
    
    private final LoanService loanService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @Operation(summary = "Get all loans")
    public ResponseEntity<ApiResponse<Page<Loan>>> getAllLoans(Pageable pageable) {
        Page<Loan> loans = loanService.getAllLoans(pageable);
        return ResponseEntity.ok(ApiResponse.success(loans));
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get loans by user")
    public ResponseEntity<ApiResponse<Page<Loan>>> getLoansByUser(@PathVariable Long userId, Pageable pageable) {
        Page<Loan> loans = loanService.getLoansByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(loans));
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @Operation(summary = "Get loans by status")
    public ResponseEntity<ApiResponse<Page<Loan>>> getLoansByStatus(@PathVariable LoanStatus status, Pageable pageable) {
        Page<Loan> loans = loanService.getLoansByStatus(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(loans));
    }
    
    @PostMapping("/user/{userId}/book/{bookId}")
    @Operation(summary = "Create a new loan")
    public ResponseEntity<ApiResponse<Loan>> createLoan(@PathVariable Long userId, @PathVariable Long bookId) {
        Loan loan = loanService.createLoan(userId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Loan created successfully", loan));
    }
    
    @PutMapping("/{loanId}/return")
    @Operation(summary = "Return a book")
    public ResponseEntity<ApiResponse<Loan>> returnBook(@PathVariable Long loanId) {
        Loan loan = loanService.returnBook(loanId);
        return ResponseEntity.ok(ApiResponse.success("Book returned successfully", loan));
    }
}
