package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;

public interface BudgetCategoryService {
    BudgetCategoryResponse createBudgetCategory(BudgetCategoryRequest budgetCategoryRequest);

    public void deleteBudgetCategory (Long budgetCategoryId);
}
