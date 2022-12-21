package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetCategoryService;
import com.example.decapay.utils.UserIdUtil;
import com.example.decapay.utils.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetCategoryServiceImpTest {

    @InjectMocks
    private BudgetCategoryServiceImp budgetCategoryService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BudgetCategoryRepository budgetCategoryRepository;

    @Mock
    private UserUtil userUtil;

   @BeforeEach
   void setUp(){}
    @Test
    void createBudgetCategory() {

        BudgetCategoryRequest budgetCategoryRequest= new BudgetCategoryRequest();
        budgetCategoryRequest.setName("Provision");

        User user=new User();

        LocalDateTime localDateTime= LocalDateTime.now();
        BudgetCategory budgetCategory= new BudgetCategory();
        budgetCategory.setId(1L);
        budgetCategory.setUser(user);
        budgetCategory.setName("Provision");
        budgetCategory.setCreatedAt(localDateTime);
        budgetCategory.setUpdatedAt(localDateTime);
        budgetCategory.setDeleted(false);

        BudgetCategoryResponse budgetCategoryResponse= new BudgetCategoryResponse();
        budgetCategoryResponse.setId(budgetCategory.getId());
        budgetCategoryResponse.setName(budgetCategory.getName());


        String email="mic@gmail.com";

        when(userUtil.getAuthenticatedUserEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        when(budgetCategoryRepository.save(any(BudgetCategory.class))).thenReturn(budgetCategory);

        budgetCategoryService.createBudgetCategory(budgetCategoryRequest);

        Assert.assertEquals(budgetCategory.getId(),budgetCategoryResponse.getId());
        Assert.assertEquals(budgetCategory.getName(),budgetCategoryResponse.getName());



    }
    @Test
    void listBudgetCategory() {

        List<BudgetCategoryResponse> budgetCategoryResponses = new ArrayList<>();

        List<BudgetCategory> budgetCategories= new ArrayList<>();

        User user = new User();
        given(userUtil.getAuthenticatedUserEmail()).willReturn("peterhamza6@gmail.com");

        given(userRepository.findByEmail("peterhamza6@gmail.com")).willReturn(Optional.of(user));

        lenient().when(budgetCategoryRepository.findByUser(user)).thenReturn(budgetCategories);

        lenient().when(budgetCategoryService.listBudgetCategory()).thenReturn(budgetCategoryResponses);

    }

}