package com.example.decapay.services.impl;
import com.example.decapay.models.Expense;
import com.example.decapay.models.LineItem;
import com.example.decapay.repositories.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class ExpensesServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpensesServiceImpl expensesService;



    @Test
    void deleteExpense() {
       LineItem lineItem =new LineItem();
        lineItem.setId(1L);
      Expense expense=new Expense();
        expense.setAmount(BigDecimal.valueOf(100000));
        expense.setId(1L);
        expense.setDeleted(false);
        expense.setLineItem(lineItem);



        given(expenseRepository.findById(anyLong())).willReturn(Optional.of(expense));

       Boolean result = expensesService.deleteExpense(expense.getId());
        assertEquals( true, result);

    }
}