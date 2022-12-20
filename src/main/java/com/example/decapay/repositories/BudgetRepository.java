package com.example.decapay.repositories;

import com.example.decapay.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findBudgetByIdAndUserId(Long budgetId, Long userId);
}
