package com.library.service;

import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.entity.User;
import com.library.enums.LoanStatus;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    private static final int MAX_LOANS_PER_USER = 5;
    private static final int LOAN_DURATION_DAYS = 14;
    
    public Page<Loan> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }
    
    public Page<Loan> getLoansByUser(Long userId, Pageable pageable) {
        return loanRepository.findByUserId(userId, pageable);
    }
    
    public Page<Loan> getLoansByStatus(LoanStatus status, Pageable pageable) {
        return loanRepository.findByStatus(status, pageable);
    }
    
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }
    
    @Transactional
    public Loan createLoan(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        
        // Check if user has reached maximum loans
        long activeLoans = loanRepository.countActiveLoansByUser(userId);
        if (activeLoans >= MAX_LOANS_PER_USER) {
            throw new BusinessException("User has reached maximum number of active loans");
        }
        
        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            throw new BusinessException("Book is not available for loan");
        }
        
        // Check if user already has this book on loan
        if (loanRepository.findActiveLoanByUserAndBook(userId, bookId).isPresent()) {
            throw new BusinessException("User already has this book on loan");
        }
        
        // Create loan
        Loan loan = Loan.builder()
                .user(user)
                .book(book)
                .loanDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(LOAN_DURATION_DAYS))
                .status(LoanStatus.ACTIVE)
                .build();
        
        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        
        return loanRepository.save(loan);
    }
    
    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = getLoanById(loanId);
        
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new BusinessException("Loan is not active");
        }
        
        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);
        
        // Update book availability
        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        
        return loanRepository.save(loan);
    }
    
    @Transactional
    public void updateOverdueLoans() {
        List<Loan> overdueLoans = loanRepository.findOverdueLoans(LocalDate.now());
        for (Loan loan : overdueLoans) {
            loan.setStatus(LoanStatus.OVERDUE);
        }
        loanRepository.saveAll(overdueLoans);
    }
}
