package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;

public interface BudgetService {

    CreateBudgetResponse createBudget(CreateBudgetRequest budgetRequest);
    void deleteBudget(Long budgetId);
}