package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetCategoryService;
import com.example.decapay.utils.UserIdUtil;
import com.example.decapay.utils.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BudgetCategoryServiceImpTest {

    @Autowired
    private BudgetCategoryService budgetCategoryService;

    @Mock
    private BudgetCategoryRepository budgetCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserIdUtil userIdUtil;
    @Mock
    private UserUtil userUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private CustomUserDetailService customUserDetailService;

    @Mock
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private TokenRepository tokenRepository;

    private BudgetCategoryRequest budgetCategoryRequest;

    @BeforeEach
    void setUp() {
         budgetCategoryService=new BudgetCategoryServiceImp(
                 budgetCategoryRepository,userRepository, userUtil
         );
        budgetCategoryRequest=new BudgetCategoryRequest();
        budgetCategoryRequest.setName("Food Stuff");

    }

    @Test
    void createBudgetCategory() {

        User user=new User();

        BudgetCategory budgetCategory= new BudgetCategory();

        String email="mic@gmail.com";

        given(userUtil.getAuthenticatedUserEmail()).willReturn(email);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        budgetCategoryService.createBudgetCategory(budgetCategoryRequest);

    }
}