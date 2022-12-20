package com.example.decapay.services.impl;

import com.example.decapay.models.Expense;
import com.example.decapay.models.LineItem;
import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.repositories.ExpenseRepository;
import com.example.decapay.repositories.LineItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {
    @Mock
    private  ExpenseRepository expenseRepository;
    @Mock
    private  LineItemRepository lineItemRepository;
    @InjectMocks
    private ExpenseServiceImpl expenseService;
    @Test
    void createExpense() {
        LineItem lineItem = new LineItem();
        lineItem.setId(1L);


        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setAmount(new BigDecimal(3000));
        expenseRequestDto.setDescription("Food stuffs");

        Expense expense = new Expense();
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setDescription(expenseRequestDto.getDescription());

        when(lineItemRepository.findById(lineItem.getId())).thenReturn(Optional.of(lineItem));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        assertEquals(expenseService.createExpense(expenseRequestDto,lineItem.getId()).getStatusCode(), HttpStatus.OK);
    }
}