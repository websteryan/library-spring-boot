package com.library.repository;

import com.library.entity.Loan;
import com.library.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    Page<Loan> findByUserId(Long userId, Pageable pageable);
    
    Page<Loan> findByStatus(LoanStatus status, Pageable pageable);
    
    @Query("SELECT l FROM Loan l WHERE l.status = 'ACTIVE' AND l.dueDate < :date")
    List<Loan> findOverdueLoans(@Param("date") LocalDate date);
    
    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId AND l.book.id = :bookId AND l.status = 'ACTIVE'")
    Optional<Loan> findActiveLoanByUserAndBook(@Param("userId") Long userId, @Param("bookId") Long bookId);
    
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.user.id = :userId AND l.status = 'ACTIVE'")
    long countActiveLoansByUser(@Param("userId") Long userId);
}
