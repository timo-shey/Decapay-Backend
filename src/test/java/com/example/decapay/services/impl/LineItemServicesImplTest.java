package com.example.decapay.services.impl;

import com.example.decapay.models.Budget;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.LineItem;
import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
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