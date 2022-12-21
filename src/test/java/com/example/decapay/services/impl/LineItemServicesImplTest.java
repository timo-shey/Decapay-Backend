package com.example.decapay.services.impl;

import com.example.decapay.models.Budget;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.LineItem;
import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.LineItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class LineItemServicesImplTest {
    @Mock
    LineItemRepository lineItemRepositoryMock;

    @InjectMocks
    LineItemServicesImpl lineItemServicesImlMcok;

    @Mock
    BudgetRepository budgetRepository;

    @Mock
    Budget budget;

    @Mock
    BudgetCategory budgetCategory;

    @Mock
    BudgetCategoryRepository budgetCategoryRepository;

    @Mock
    LineItemRequestDto lineItemRequestDto;





    @Test
    void createLineItem() {

        LineItem newLineItem = new LineItem();
        newLineItem.setBudgetCategory(new BudgetCategory());
        newLineItem.setProjectedAmount(new BigDecimal("50000"));
        newLineItem.setBudget(new Budget());
        lineItemRepositoryMock.save(newLineItem);


        LineItemResponseDto lineItemResponseDto = new LineItemResponseDto();
        lineItemResponseDto.setLineItemId(2L);
        lineItemResponseDto.setBudgetAmount(new BigDecimal("2000"));
        lineItemResponseDto.setBudgetCategoryName("example of category");
        lineItemResponseDto.setProjectedAmount(new BigDecimal("5000"));
        lineItemResponseDto.setTotalAmountSpent(new BigDecimal("7000"));

        when(budgetRepository.findById(budget.getId())).thenReturn(Optional.of(budget));
        when(budgetCategoryRepository.findById(budgetCategory.getId())).thenReturn(Optional.of(budgetCategory));

        assertEquals(lineItemServicesImlMcok.createLineItem(lineItemRequestDto).getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void updateLineItem() {
        Long lineItemId = 1L;
        LineItemRequestDto lineItemRequestDto = LineItemRequestDto.builder()
                .projectedAmount(new BigDecimal("20000"))
                .build();

        LineItem lineItem = new LineItem();
        lineItem.setId(1L);
        lineItem.setProjectedAmount(new BigDecimal("20000"));
        lineItem.setBudget(new Budget());
        lineItem.setBudgetCategory(new BudgetCategory());

        when(lineItemRepositoryMock.findById(lineItemId)).thenReturn(Optional.of(lineItem));
        when(lineItemRepositoryMock.save(any(LineItem.class))).thenReturn(lineItem);

        assertEquals(lineItemServicesImlMcok.updateLineItem(lineItemRequestDto, lineItemId).getStatusCode(), HttpStatus.OK);
    }
}