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
import java.util.Arrays;
import java.util.List;
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

        LineItemRequestDto lineItemRequestDto = LineItemRequestDto.builder()
                .projectedAmount(new BigDecimal("20000"))
                .budgetCategoryId(1L)
                .budgetId(2L)
                .build();

        LineItem lineItem = new LineItem();
        lineItem.setId(1L);
        lineItem.setProjectedAmount(new BigDecimal("20000"));
        lineItem.setBudget(new Budget());
        lineItem.setBudgetCategory(new BudgetCategory());

        when(budgetRepository.findById(lineItemRequestDto.getBudgetId())).thenReturn(Optional.of(new Budget()));
        when(budgetCategoryRepository.findById(lineItemRequestDto.getBudgetCategoryId())).thenReturn(Optional.of(new BudgetCategory()));
        when(lineItemRepositoryMock.save(any(LineItem.class))).thenReturn(lineItem);

        assertEquals(lineItemServicesImlMcok.createLineItem(lineItemRequestDto).getLineItemId(),  1L);


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

    @Test
    void getLineItems() {
        LineItem lineItem1 = new LineItem();
        lineItem1.setId(1L);
        lineItem1.setProjectedAmount(new BigDecimal("20000"));
        lineItem1.setBudget(new Budget());
        lineItem1.setBudgetCategory(new BudgetCategory());

        LineItem lineItem2 = new LineItem();
        lineItem2.setId(2L);
        lineItem2.setProjectedAmount(new BigDecimal("30000"));
        lineItem2.setBudget(new Budget());
        lineItem2.setBudgetCategory(new BudgetCategory());

        List<LineItem> lineItems = Arrays.asList(lineItem1, lineItem2);
        when(lineItemRepositoryMock.findAll()).thenReturn(lineItems);

        List<LineItemResponseDto> lineItemResponseDtos = lineItemServicesImlMcok.getLineItems();
        assertEquals(lineItemResponseDtos.size(), 2);
        assertEquals(lineItemResponseDtos.get(0).getLineItemId(), 1L);
        assertEquals(lineItemResponseDtos.get(1).getLineItemId(), 2L);
    }

}