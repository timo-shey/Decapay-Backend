package com.example.decapay.services.impl;

import com.example.decapay.models.Expense;
import com.example.decapay.repositories.ExpenseRepository;
import com.example.decapay.services.ExpensesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImpl implements ExpensesService {
   public final  ExpenseRepository expenseRepository;
@Override
    public Boolean deleteExpense(Long id){

    Expense expense=expenseRepository.findById(id).orElseThrow(()->new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
        return true;

    }
}
