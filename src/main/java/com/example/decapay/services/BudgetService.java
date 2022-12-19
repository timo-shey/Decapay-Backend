package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetDto;

public interface BudgetService {
    void deleteBudget(Long budgetId);

    BudgetDto updateBudget(BudgetDto budgetDto, Long budgetId);
}