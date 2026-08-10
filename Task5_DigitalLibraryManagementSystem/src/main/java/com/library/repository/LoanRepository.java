package com.library.repository;

import com.library.model.Loan;
import com.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserOrderByIssueDateDesc(User user);
    List<Loan> findByReturnDateIsNull();
    List<Loan> findByFinePaidFalseAndFineAmountGreaterThan(double amount);
}
