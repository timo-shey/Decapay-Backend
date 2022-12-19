package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import org.springframework.http.ResponseEntity;

public interface BudgetCategoryService {
    ResponseEntity<String> createBudgetCategory(BudgetCategoryRequest budgetCategoryRequest);
}
