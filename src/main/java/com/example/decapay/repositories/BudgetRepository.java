package com.example.decapay.repositories;

import com.example.decapay.models.Budget;
import com.example.decapay.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Page<Budget> findAllByUser(User user, Pageable pageable);
}
