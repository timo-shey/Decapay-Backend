package com.example.decapay.services.impl;

import com.example.decapay.models.Budget;
import com.example.decapay.models.User;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    private BudgetService budgetService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserUtil userUtil;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetServiceImpl(budgetRepository, userService, userUtil);
    }

    @Test
    void testDeleteBudget() {

        User user = new User();
        user.setId(1L);
        user.setEmail("tester@email.com");

        Budget budget = new Budget();
        budget.setId(1L);
        budget.setUser(user);

        //given
        given(userUtil.getAuthenticatedUserEmail()).willReturn("tester@email.com");
        given(userService.getUserByEmail("tester@email.com")).willReturn(user);
        given(budgetRepository.findById(1L)).willReturn(Optional.of(budget));

        //when
        budgetService.deleteBudget(1L);

        //then
        verify(budgetRepository).delete(budget);
    }
}