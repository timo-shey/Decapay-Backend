package com.example.decapay.pojos.expenseDto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class ExpenseRequestDto {
    @Min(1)
    private BigDecimal amount;
    @NotBlank(message = "Enter a description")
    private String description;

}
