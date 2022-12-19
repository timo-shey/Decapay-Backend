package com.example.decapay.services.impl;

import com.example.decapay.enums.BudgetPeriod;
import com.example.decapay.models.Budget;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
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




        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setBudgetId(1l);
        budgetDto.setTitle("hello");
        budgetDto.setAmount(BigDecimal.valueOf(1234.00));
        budgetDto.setDescription("hello world");
        budgetDto.setBudgetPeriod(BudgetPeriod.ANNUAL);

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



    @Test
    void updateBudgetTest() {
        BudgetDto budgetDto1 = new BudgetDto();
        budgetDto1.setBudgetId(1l);
        budgetDto1.setTitle("hello");
        budgetDto1.setAmount(BigDecimal.valueOf(1234.00));
        budgetDto1.setDescription("hello world");
        budgetDto1.setBudgetPeriod(BudgetPeriod.ANNUAL);



        User user = new User();
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setTitle("hello");
        budget.setAmount(BigDecimal.valueOf(1234.44));
        budget.setDescription("hello world");
        budget.setBudgetPeriod(BudgetPeriod.ANNUAL);

        // given
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));
        budgetService.updateBudget(budgetDto1,1L);

        verify(budgetRepository).save(budget);
    }
}