package com.example.decapay.services.impl;

import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.exceptions.ValidationException;
import com.example.decapay.models.LineItem;
import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
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
