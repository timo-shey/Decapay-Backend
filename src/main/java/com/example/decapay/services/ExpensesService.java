package com.example.decapay.services;

import org.springframework.http.ResponseEntity;

public interface ExpensesService {
    Boolean deleteExpense(Long id);
}
