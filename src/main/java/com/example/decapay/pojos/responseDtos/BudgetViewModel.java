package com.example.decapay.pojos.responseDtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author Ikechi Ucheagwu
 * @created 19/12/2022 - 00:16
 * @project DecaPay-Java012-Backend
 */

@Data
public class BudgetViewModel {
    private BigDecimal amount;
    private BigDecimal totalAmountSpent;
    private BigDecimal percentage;
    List<LineItemRest> lineItemRests;
}
