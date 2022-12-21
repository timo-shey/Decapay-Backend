package com.example.decapay.services.impl;

import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetCategoryService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor

public class BudgetCategoryServiceImp implements BudgetCategoryService {

    private final BudgetCategoryRepository budgetCategoryRepository;
    private  final UserRepository userRepository;
    private final UserUtil userUtil;

    @Override
    public BudgetCategoryResponse createBudgetCategory(BudgetCategoryRequest budgetCategoryRequest) {

        String email= userUtil.getAuthenticatedUserEmail();

        User user= userRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));

        BudgetCategory budgetCategory= new BudgetCategory();

        budgetCategory.setName(budgetCategoryRequest.getName());
        budgetCategory.setUser(user);

        budgetCategoryRepository.save(budgetCategory);

       return BudgetCategoryResponse.mapFrom(budgetCategory);

    }
}
