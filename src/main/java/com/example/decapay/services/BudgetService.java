package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;

public interface BudgetService {

    CreateBudgetResponse createBudget(CreateBudgetRequest budgetRequest);
    CreateBudgetResponse fetchBudgetById(Long budgetId);
    void deleteBudget(Long budgetId);

    BudgetDto updateBudget(BudgetDto budgetDto, Long budgetId);
}