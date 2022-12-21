package com.example.decapay.services;

import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;

import java.util.List;

public interface BudgetCategoryService {
    BudgetCategoryResponse createBudgetCategory(BudgetCategoryRequest budgetCategoryRequest);
    List<BudgetCategoryResponse> listBudgetCategory();

}
