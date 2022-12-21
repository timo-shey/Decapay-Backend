package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.BudgetRest;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;

import java.util.List;

public interface BudgetService {
    List<BudgetRest> getBudgets(int page, int limit);
    CreateBudgetResponse createBudget(CreateBudgetRequest budgetRequest);
    CreateBudgetResponse fetchBudgetById(Long budgetId);

    void deleteBudget(Long budgetId);
}