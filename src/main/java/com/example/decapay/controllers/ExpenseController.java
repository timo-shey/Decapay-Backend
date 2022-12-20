package com.example.decapay.controllers;

import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import com.example.decapay.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    @PostMapping("/create-expense/{lineId}")
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto, @PathVariable Long lineId){
        return expenseService.createExpense(expenseRequestDto,lineId);
    }
}
