package com.example.decapay.services.impl;

import com.example.decapay.models.Budget;
import com.example.decapay.models.LineItem;
import com.example.decapay.models.User;
import com.example.decapay.pojos.responseDtos.BudgetRest;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    User user;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetServiceImpl(budgetRepository, userService, userUtil);
        user = new User();
        user.setId(1L);
        user.setEmail("tester@email.com");
    }

    @Test
    @DisplayName("List of Budget for a User")
    void getBudgets() {
        List<Budget> budgets = createBudgetList();

        Pageable pageable = PageRequest.of(0, 2);
        int start = Math.min((int) pageable.getOffset(), budgets.size());
        int end = Math.min((start + pageable.getPageSize()), budgets.size());
        Page<Budget> budgetPage = new PageImpl<>(budgets.subList(start, end), pageable, budgets.size());

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("tester@email.com");
        when(userService.getUserByEmail("tester@email.com")).thenReturn(user);
        when(budgetRepository.findAllByUser(user, pageable)).thenReturn(budgetPage);

        List<BudgetRest> budgetRest = budgetService.getBudgets(0, 2);

        assertNotNull(budgetRest);
        assertEquals(2, budgetRest.size());
        assertEquals(new BigDecimal("0.75"), budgetRest.get(0).getPercentage());
        assertEquals(new BigDecimal("0.94"), budgetRest.get(0).getLineItemRests().get(0).getPercentageSpentSoFar());
        verify(budgetRepository, times(1)).findAllByUser(any(User.class), any(Pageable.class));

    }

    private List<Budget> createBudgetList() {
        Budget budget = new Budget();
        budget.setDescription("Budget-One");
        budget.setAmount(new BigDecimal(1000));

        List<LineItem> lineItems = new ArrayList<>();
        LineItem lineItem = new LineItem();
        lineItem.setProjectedAmount(new BigDecimal(800));
        lineItem.setTotalAmountSpent(new BigDecimal(750));
        lineItems.add(lineItem);
        budget.setLineItems(lineItems);

        Budget budget2 = new Budget();
        budget2.setDescription("Budget-Two");
        budget2.setAmount(new BigDecimal(2000));

        List<LineItem> lineItems2 = new ArrayList<>();
        LineItem lineItem2 = new LineItem();
        lineItem2.setProjectedAmount(new BigDecimal(500));
        lineItem2.setTotalAmountSpent(new BigDecimal(450));
        lineItems2.add(lineItem2);
        budget2.setLineItems(lineItems2);

        List<Budget> budgets = new ArrayList<>();
        budgets.add(budget);
        budgets.add(budget2);

        return budgets;
    }

    @Test
    void testDeleteBudget() {
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