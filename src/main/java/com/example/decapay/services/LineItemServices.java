package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import org.springframework.http.ResponseEntity;


public interface LineItemServices {

    ResponseEntity<LineItemResponseDto> updateLineItem(LineItemRequestDto lineItemRequestDto, Long lineItemId);
}