package com.example.decapay.services;

import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import org.springframework.http.ResponseEntity;

public interface ExpenseService {
    ResponseEntity<ExpenseResponseDto> createExpense(ExpenseRequestDto expenseRequestDto, Long lineId);
}
