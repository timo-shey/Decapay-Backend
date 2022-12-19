package com.example.decapay.services;

import com.example.decapay.pojos.responseDtos.BudgetRest;

import java.util.List;

public interface BudgetService {
    List<BudgetRest> getBudgets(int page, int limit);
    void deleteBudget(Long budgetId);

}