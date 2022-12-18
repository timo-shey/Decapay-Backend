package com.example.decapay.services;

import com.example.decapay.models.Budget;

import java.util.List;

public interface BudgetService {
    List<Budget> getBudgets(int page, int limit);
    void deleteBudget(Long budgetId);

}