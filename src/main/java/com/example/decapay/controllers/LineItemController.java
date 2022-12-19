package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import com.example.decapay.services.LineItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/line-item")
public class LineItemController {

    private final LineItemServices lineItemServices;

    @PostMapping("/update")
    public ResponseEntity<LineItemResponseDto> updateALineItem(@RequestBody LineItemRequestDto lineItemRequestDto) {
        return lineItemServices.updateLineItem(lineItemRequestDto);
    }
}