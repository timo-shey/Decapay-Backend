

package com.example.decapay.pojos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineItemRequestDto {

    @NotBlank
    private Long budgetCategoryId;

    @NotBlank
    private Long budgetId;

    @NotBlank
    private BigDecimal projectedAmount;

}