package com.example.decapay.services.impl;

import com.example.decapay.exceptions.AuthenticationException;
import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.models.Budget;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final UserUtil userUtil;

    @Override
    public void deleteBudget(Long budgetId) {

        String email = userUtil.getAuthenticatedUserEmail();

        User user = userService.getUserByEmail(email);

        Budget budget = getBudget(budgetId);

        boolean authorized = budget.getUser().getId().equals(user.getId());

        if (!authorized){
            throw new AuthenticationException("Action Not Authorized");
        }

        budgetRepository.delete(budget);
    }

    @Override
    public BudgetDto updateBudget(BudgetDto budgetDto, Long budgetId) {

        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        HttpStatus.BAD_REQUEST, "Budget with id: " + budgetId + " not found"
                ));

        budget.setTitle(budgetDto.getTitle());
        budget.setAmount(budgetDto.getAmount());
        budget.setDescription(budgetDto.getDescription());
        budget.setUpdatedAt(budgetDto.getUpdatedAt());
        budget.setBudgetPeriod(budgetDto.getBudgetPeriod());
        budget.setStartDate(LocalDateTime.now());
        budget.setEndDate(LocalDateTime.now());

        budgetRepository.save(budget);

        return budgetDto;
    }

    private Budget getBudget(Long budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.BAD_REQUEST, "Budget with id: " + budgetId + " Not Found"));
    }
}