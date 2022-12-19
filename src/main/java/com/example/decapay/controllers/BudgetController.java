package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.BudgetDto;
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

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long budgetId){
        budgetService.deleteBudget(budgetId);
        return ResponseEntity.ok("Budget Deleted Successfully");
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<BudgetDto> updateBudget(@Valid @RequestBody BudgetDto budgetDto, @PathVariable Long budgetId) {
        BudgetDto response = budgetService.updateBudget(budgetDto,budgetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}