package com.example.decapay.services.impl;

import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetCategoryService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public List<BudgetCategoryResponse> listBudgetCategory() {
        String email = userUtil.getAuthenticatedUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found", HttpStatus.NOT_FOUND,"Invalid request"));



        List<BudgetCategory> category = budgetCategoryRepository.findByUser(user);
        List<BudgetCategoryResponse> budgetCategoryResponses = new ArrayList<>();

        category.forEach(budgetCategory -> {
            BudgetCategoryResponse budgetCategoryResponse = new BudgetCategoryResponse();

            budgetCategoryResponse.setId(budgetCategory.getId());
            budgetCategoryResponse.setName(budgetCategory.getName());

            budgetCategoryResponses.add(budgetCategoryResponse);
        });

        return budgetCategoryResponses;
    }
}
