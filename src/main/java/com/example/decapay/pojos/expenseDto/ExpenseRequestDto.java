package com.example.decapay.pojos.expenseDto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
public class ExpenseRequestDto {
    @Min(1)
    private BigDecimal amount;
    @NotBlank(message = "Enter a description")
    private String description;

}
