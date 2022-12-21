package com.example.decapay.services;

import com.example.decapay.models.Expense;
import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpenseService {
    ResponseEntity<ExpenseResponseDto> createExpense(ExpenseRequestDto expenseRequestDto, Long lineId);

    Boolean deleteExpense(Long id);

    List<Expense> getExpenses(Long lineId);
}
