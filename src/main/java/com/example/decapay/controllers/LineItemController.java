package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import com.example.decapay.services.LineItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/line-items")
public class LineItemController {

    private final LineItemServices lineItemServices;

    @PostMapping("/update/{lineItemId}")
    public ResponseEntity<LineItemResponseDto> updateALineItem(@RequestBody LineItemRequestDto lineItemRequestDto, @Valid @PathVariable Long lineItemId) {
        return lineItemServices.updateLineItem(lineItemRequestDto, lineItemId);
    }
}