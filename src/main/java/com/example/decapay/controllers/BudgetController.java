package com.example.decapay.controllers;

import com.example.decapay.models.Budget;
import com.example.decapay.services.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Budget> budgets = budgetService.getBudgets(page, limit);
        return ResponseEntity.ok(budgets);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long budgetId) {
        budgetService.deleteBudget(budgetId);
        return ResponseEntity.ok("Budget Deleted Successfully");
    }
}