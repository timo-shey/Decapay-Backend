package com.example.decapay.services.impl;

import com.example.decapay.exceptions.AuthenticationException;
import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.models.Budget;
import com.example.decapay.models.LineItem;
import com.example.decapay.models.User;
import com.example.decapay.pojos.responseDtos.BudgetRest;
import com.example.decapay.pojos.responseDtos.LineItemRest;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final UserUtil userUtil;

    @Override
    public List<BudgetRest> getBudgets(int page, int limit) {
        String email = userUtil.getAuthenticatedUserEmail();
        User user = userService.getUserByEmail(email);

        if (page > 0) page = page - 1;

        Pageable pageable = PageRequest.of(page, limit);
        Page<Budget> budgetPage = budgetRepository.findAllByUser(user, pageable);

        List<Budget> budgets = budgetPage.getContent();

        return budgetRest(budgets);
    }

    private List<BudgetRest> budgetRest(List<Budget> budgets) {
        List<BudgetRest> budgetRestList = new ArrayList<>();

        budgets.forEach(budget -> {
            BudgetRest budgetRest = new BudgetRest();
            budgetRest.setAmount(budget.getAmount());

            List<LineItem> lineItems = budget.getLineItems();
            BigDecimal totalAmountSpent = lineItems.stream()
                    .map(LineItem::getTotalAmountSpent)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetRest.setTotalAmountSpent(totalAmountSpent);

            BigDecimal percentage = totalAmountSpent.divide(budget.getAmount(), new MathContext(2));
            budgetRest.setPercentage(percentage);
            budgetRest.setLineItemRests(getLineItemRest(lineItems));
            budgetRestList.add(budgetRest);
        });

        return budgetRestList;
    }

    private List<LineItemRest> getLineItemRest(List<LineItem> lineItems) {
        List<LineItemRest> lineItemRests = new ArrayList<>();

        lineItems.forEach(lineItem -> {
            LineItemRest lineItemRest = new LineItemRest();

            BigDecimal totalAmountSoFar = lineItem.getTotalAmountSpent();
            BigDecimal percentageSoFar = totalAmountSoFar.divide(lineItem.getProjectedAmount(), new MathContext(2));
            lineItemRest.setAmountSpentSoFar(totalAmountSoFar);
            lineItemRest.setPercentageSpentSoFar(percentageSoFar);
            lineItemRests.add(lineItemRest);
        });

        return lineItemRests;
    }

    @Override
    public void deleteBudget(Long budgetId) {

        String email = userUtil.getAuthenticatedUserEmail();

        User user = userService.getUserByEmail(email);

        Budget budget = getBudget(budgetId);

        boolean authorized = budget.getUser().getId().equals(user.getId());

        if (!authorized) {
            throw new AuthenticationException("Action Not Authorized");
        }

        budgetRepository.delete(budget);
    }

    private Budget getBudget(Long budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.BAD_REQUEST, "Budget with id: " + budgetId + " Not Found"));
    }
}