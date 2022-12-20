package com.example.decapay.pojos.responseDtos;
import com.example.decapay.models.BudgetCategory;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter


public class BudgetCategoryResponse {
    private  Long id;
    private  String name;


    public static BudgetCategoryResponse mapFrom(BudgetCategory budgetCategory){

        BudgetCategoryResponse budgetCategoryResponse= new BudgetCategoryResponse();
        budgetCategoryResponse.setId(budgetCategoryResponse.getId());
        budgetCategoryResponse.setName(budgetCategoryResponse.getName());

        return budgetCategoryResponse;
    }

}
