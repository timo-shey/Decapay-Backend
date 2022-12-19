package com.example.decapay.pojos.responseDtos;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @author Ikechi Ucheagwu
 * @created 19/12/2022 - 00:16
 * @project DecaPay-Java012-Backend
 */

@Data
public class LineItemRest {
    private BigDecimal amountSpentSoFar;
    private BigDecimal percentageSpentSoFar;
}
