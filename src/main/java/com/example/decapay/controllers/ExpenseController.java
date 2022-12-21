package com.example.decapay.controllers;

import com.example.decapay.models.Expense;
import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import com.example.decapay.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    @PostMapping("/create-expense/{lineId}")
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto, @PathVariable Long lineId){
        return expenseService.createExpense(expenseRequestDto,lineId);
    }

    @DeleteMapping("/delete_expense/{id}")
    public ResponseEntity<Boolean> deleteExpense(@PathVariable Long id){
        return new ResponseEntity<>( expenseService.deleteExpense(id), HttpStatus.OK);
    }

    @GetMapping(value = "/expenses")
    public ResponseEntity<List<Expense>> getExpenses(@PathVariable Long lineId) {
        List<Expense> expenses = expenseService.getExpenses(lineId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

}
