package com.example.decapay.pojos.requestDtos;

import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter

public class BudgetCategoryRequest {
    @NotBlank(message ="Category name is required")
    private String name;
}
