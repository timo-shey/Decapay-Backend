package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import org.springframework.http.ResponseEntity;

public interface BudgetCategoryService {
    BudgetCategoryResponse createBudgetCategory(BudgetCategoryRequest budgetCategoryRequest);
}
