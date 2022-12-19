

package com.example.decapay.pojos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineItemRequestDto {

    private Long lineItemId;

    private Long budgetCategoryId;

    private Long budgetId;

    private BigDecimal projectedAmount;

//    private BigDecimal totalAmountSpent;

}