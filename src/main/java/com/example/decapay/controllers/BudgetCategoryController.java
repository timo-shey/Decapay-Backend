package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import com.example.decapay.services.BudgetCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteBudgetCategory(@PathVariable Long category_id){
        budgetCategoryService.deleteBudgetCategory(category_id);
        return ResponseEntity.ok("Category successfully deleted");
    }
}
