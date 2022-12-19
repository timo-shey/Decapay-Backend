package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;
import com.example.decapay.services.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<CreateBudgetResponse> createBudget(@Valid @RequestBody CreateBudgetRequest request){
        return new ResponseEntity<>(budgetService.createBudget(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long budgetId){
        budgetService.deleteBudget(budgetId);
        return ResponseEntity.ok("Budget Deleted Successfully");
    }
}