package com.example.decapay.services.impl;

import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.exceptions.ValidationException;
import com.example.decapay.models.Budget;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.LineItem;
import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.LineItemRepository;
import com.example.decapay.services.LineItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LineItemServicesImpl implements LineItemServices {

    private final LineItemRepository lineItemRepository;
    private final BudgetRepository budgetRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;






    public ResponseEntity<LineItemResponseDto> createLineItem (LineItemRequestDto lineItemRequestDto) {
        Budget budget = budgetRepository.findById(lineItemRequestDto.getBudgetId()).orElseThrow( ()
                -> new ResourceNotFoundException("cannot create line item", HttpStatus.FORBIDDEN, "please select a budget"));
        BudgetCategory budgetCategory = budgetCategoryRepository.findById(lineItemRequestDto.getBudgetCategoryId()).orElseThrow(()
                -> new ResourceNotFoundException("line item cannot be created", HttpStatus.FORBIDDEN, "please select a budget category"));
        LineItem newLineItem = new LineItem();

        newLineItem.setBudgetCategory(budgetCategory);
        newLineItem.setProjectedAmount(lineItemRequestDto.getProjectedAmount());
        newLineItem.setBudget(budget);

        lineItemRepository.save(newLineItem);

        LineItemResponseDto lineItemResponseDto = new LineItemResponseDto();
        lineItemResponseDto.setLineItemId(lineItemResponseDto.getLineItemId());
        lineItemResponseDto.setBudgetAmount(lineItemResponseDto.getBudgetAmount());
        lineItemResponseDto.setBudgetCategoryName(lineItemResponseDto.getBudgetCategoryName());
        lineItemResponseDto.setProjectedAmount(lineItemResponseDto.getProjectedAmount());
        lineItemResponseDto.setTotalAmountSpent(lineItemResponseDto.getTotalAmountSpent());

        return new ResponseEntity<>(lineItemResponseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LineItemResponseDto> updateLineItem(LineItemRequestDto lineItemRequestDto, Long lineItemId) {

        LineItemResponseDto lineItemResponseDto = null;

        LineItem lineItem = lineItemRepository.findById(lineItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Line item does not exist", HttpStatus.NOT_FOUND, "Please select a valid line item"));
        lineItem.setProjectedAmount(lineItemRequestDto.getProjectedAmount());
        lineItem = lineItemRepository.save(lineItem);


        lineItemResponseDto = LineItemResponseDto.builder()
                .lineItemId(lineItem.getId())
                .budgetAmount(lineItem.getBudget().getAmount())
                .budgetCategoryName(lineItem.getBudgetCategory().getName())
                .projectedAmount(lineItem.getProjectedAmount())
                .totalAmountSpent(lineItem.getTotalAmountSpent())
                .build();

        return new ResponseEntity<>(lineItemResponseDto, HttpStatus.OK);
    }
}
