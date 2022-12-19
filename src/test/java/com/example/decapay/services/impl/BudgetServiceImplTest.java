package com.example.decapay.services.impl;

import com.example.decapay.enums.BudgetPeriod;
import com.example.decapay.models.Budget;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.DateParser;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testCreateBudget() {
        User activeUser = new User();
        activeUser.setId(2L);
        activeUser.setEmail("mybudget@email.com");
        userRepository.save(activeUser);
        LocalDate startDate = DateParser.parseDate("2022-12-19");
        LocalDate endDate = DateParser.parseDate("2023-01-19");
        Budget budget = new Budget();
        budget.setAmount(BigDecimal.valueOf(1000));
        budget.setBudgetPeriod(BudgetPeriod.MONTHLY);
        budget.setTitle("Test");
        budget.setId(2L);
        budget.setDescription("Testing....");
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setUser(activeUser);
        given(userUtil.getAuthenticatedUserEmail()).willReturn("mybudget@email.com");
        given(userService.getUserByEmail("mybudget@email.com")).willReturn(activeUser);
        CreateBudgetRequest budgetRequest = CreateBudgetRequest.mapBudgetToCreateBudgetRequest(budget);
        CreateBudgetResponse returnedBudget = budgetService.createBudget(budgetRequest);
        assertEquals(budgetRequest.getAmount(), returnedBudget.getAmount());
        assertEquals(budgetRequest.getTitle(), returnedBudget.getTitle());
        assertEquals(budgetRequest.getPeriod(), returnedBudget.getPeriod());
        assertEquals(budgetRequest.getDescription(), returnedBudget.getDescription());
    }

    @Test
    void testFetchBudget() {
        User activeUser = new User();
        activeUser.setId(2L);
        activeUser.setEmail("mybudget@email.com");
        userRepository.save(activeUser);
        Budget budget = new Budget();
        budget.setId(2L);
        budget.setUser(activeUser);
        budgetRepository.save(budget);
        given(userUtil.getAuthenticatedUserEmail()).willReturn("mybudget@email.com");
        given(userService.getUserByEmail("mybudget@email.com")).willReturn(activeUser);
        given(budgetRepository.findBudgetByIdAndUserId(2L, 2L)).willReturn(Optional.of(budget));
        CreateBudgetResponse returnedBudget = budgetService.fetchBudgetById(2L);
        assertEquals(budget.getDescription(), returnedBudget.getDescription());
        assertEquals(budget.getTitle(), returnedBudget.getTitle());
        assertEquals(budget.getAmount(), returnedBudget.getAmount());
        assertEquals(String.valueOf(budget.getBudgetPeriod()), returnedBudget.getPeriod());
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