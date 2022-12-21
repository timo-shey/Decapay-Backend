package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.services.BudgetCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/budgets/category")

public class BudgetCategoryController {

    private final BudgetCategoryService budgetCategoryService;

    @PostMapping("/create")
    public ResponseEntity<String> createBudgetCategory(@Valid @RequestBody BudgetCategoryRequest budgetCategoryRequest){
        budgetCategoryService.createBudgetCategory(budgetCategoryRequest);
        return ResponseEntity.ok("Budget category created");
    }
}
