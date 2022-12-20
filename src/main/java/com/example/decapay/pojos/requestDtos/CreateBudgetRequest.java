package com.example.decapay.pojos.requestDtos;

import com.example.decapay.enums.BudgetPeriod;
import com.example.decapay.exceptions.ValidationException;
import com.example.decapay.models.Budget;
import com.example.decapay.utils.DateParser;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

        String period = request.getPeriod();
        budget.setBudgetPeriod(BudgetPeriod.valueOf(period));


        LocalDate year = LocalDate.of(request.getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());

        if (request.getPeriod().equals(String.valueOf(BudgetPeriod.ANNUAL))){
            if(year.isBefore(LocalDate.now()))
                throw new ValidationException("Invalid Date. Please enter a new year.");
            else {
                budget.setStartDate(year.minusYears(1L));
                budget.setEndDate(year);
            }
        } else if (request.getPeriod().equals(String.valueOf(BudgetPeriod.MONTHLY))){
            LocalDate month = LocalDate.of(request.getYear(), request.getMonth(), LocalDate.now().getDayOfMonth());
            budget.setStartDate(month.minusMonths(1L));
            budget.setEndDate(month);

        } else if (request.getPeriod().equals(String.valueOf(BudgetPeriod.WEEKLY))) {
            budget.setStartDate(DateParser.parseDate(request.getBudgetStartDate()).minusWeeks(1L));
            budget.setEndDate(DateParser.parseDate(request.getBudgetStartDate()));

        } else if (request.getPeriod().equals(String.valueOf(BudgetPeriod.DAILY))) {
            budget.setStartDate(DateParser.parseDate(request.getBudgetStartDate()));
            budget.setEndDate(DateParser.parseDate(request.getBudgetStartDate()).plusDays(1L));

        } else if (request.getPeriod().equals(String.valueOf(BudgetPeriod.CUSTOM))) {
            if(DateParser.parseDate(request.getBudgetEndDate()).isBefore
                    (DateParser.parseDate(request.getBudgetStartDate())))
                throw new ValidationException("Invalid Date. Please enter a valid date.");
            else {
                budget.setStartDate(DateParser.parseDate(request.getBudgetStartDate()));
                budget.setEndDate(DateParser.parseDate(request.getBudgetEndDate()));
            }
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
