package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.BudgetDtoResponse;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;

public interface BudgetService {

    CreateBudgetResponse createBudget(CreateBudgetRequest budgetRequest);

    CreateBudgetResponse fetchBudgetById(Long budgetId);
    void deleteBudget(Long budgetId);

    BudgetDtoResponse updateBudget(BudgetDto budgetDto, Long budgetId);
}