package com.example.decapay.services.impl;

import com.example.decapay.models.Expense;
import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import com.example.decapay.repositories.ExpenseRepository;
import com.example.decapay.services.ExpenseService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserUtil userUtil;


    @Override
    public ResponseEntity<ExpenseResponseDto> createExpense(ExpenseRequestDto expenseRequestDto, Long lineId) {

        Expense expense = new Expense();
        expense.setAmount(expenseRequestDto.getAmount());

        expenseRepository.save(expense);

        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setAmount(expense.getAmount());
        expenseResponseDto.setCreatedAt(expense.getCreatedAt());

        return ResponseEntity.ok(expenseResponseDto);
    }
}
