package com.example.decapay.pojos.requestDtos;

import com.example.decapay.enums.BudgetPeriod;
import com.example.decapay.models.Budget;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor
public class CreateBudgetRequest {
    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;
    @NotNull(message = "Budget Period is required. Accepted input includes: ANNUAL, MONTHLY, WEEKLY, DAILY, CUSTOM")
    private String period;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format")
    private String budgetStartDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format")
    private String budgetEndDate;
    @NotBlank
    private String description;
    @Min(1)
    @Max(12)
    private int month;
    @Min(2022)
    @Max(2100)
    private int year;

    public static Budget mapCreateBudgetRequestToBudget (CreateBudgetRequest request){
        Budget budget = new Budget();
        budget.setTitle(request.getTitle());
        budget.setAmount(request.getAmount());
        budget.setDescription(request.getDescription());
        budget.setBudgetPeriod(BudgetPeriod.valueOf(request.getPeriod()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate budgetStartDate = LocalDate.parse(request.getBudgetStartDate(), formatter);
        LocalDate budgetEndDate = LocalDate.parse(request.getBudgetEndDate(), formatter);
        if (request.getPeriod().equalsIgnoreCase(String.valueOf(BudgetPeriod.ANNUAL))){
            budget.setStartDate(LocalDate.now());
            budget.setEndDate(LocalDate.ofEpochDay(request.getYear()));
        } else if (request.getPeriod().equalsIgnoreCase(String.valueOf(BudgetPeriod.MONTHLY))){
            budget.setStartDate(LocalDate.now());
            budget.setEndDate(LocalDate.ofEpochDay(request.getMonth()));
        } else if (request.getPeriod().equalsIgnoreCase(String.valueOf(BudgetPeriod.WEEKLY))) {
            budget.setStartDate(LocalDate.now());
            budget.setEndDate(budgetEndDate);
        } else if (request.getPeriod().equalsIgnoreCase(String.valueOf(BudgetPeriod.DAILY))) {
            budget.setStartDate(budgetStartDate);
        } else if (request.getPeriod().equalsIgnoreCase(String.valueOf(BudgetPeriod.CUSTOM))) {
            budget.setStartDate(budgetStartDate);
            budget.setEndDate(budgetEndDate);
        }
        return budget;
    }

    public static CreateBudgetRequest mapBudgetToCreateBudgetRequest (Budget request){
        CreateBudgetRequest budget = new CreateBudgetRequest();
        budget.setTitle(request.getTitle());
        budget.setAmount(request.getAmount());
        budget.setDescription(request.getDescription());
        budget.setPeriod(String.valueOf(request.getBudgetPeriod()));
        budget.setBudgetStartDate(String.valueOf(request.getStartDate()));
        budget.setBudgetEndDate(String.valueOf(request.getEndDate()));
        return budget;
    }
}
