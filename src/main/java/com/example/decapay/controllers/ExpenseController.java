package com.example.decapay.controllers;

import com.example.decapay.services.ExpensesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpensesService expensesService;
@DeleteMapping("/delete_expense/{id}")
    public ResponseEntity<Boolean> deleteExpense(@PathVariable Long id){
    return new ResponseEntity<>( expensesService.deleteExpense(id), HttpStatus.OK);
}
}
