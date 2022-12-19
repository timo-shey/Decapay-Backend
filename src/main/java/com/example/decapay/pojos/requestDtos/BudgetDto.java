package com.example.decapay.pojos.requestDtos;

import com.example.decapay.enums.BudgetPeriod;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BudgetDto {
    private Long budgetId;
    @NotEmpty(message = "title field required")
    private String title;

    @NotEmpty(message = "amount required")
    private BigDecimal amount;

    private BudgetPeriod budgetPeriod;

    private LocalDateTime updatedAt;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotEmpty(message = "description required")
    private String description;
}
