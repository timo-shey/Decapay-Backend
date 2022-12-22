package com.example.decapay.services.impl;

import com.example.decapay.enums.BudgetPeriod;
import com.example.decapay.exceptions.AuthenticationException;
import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.exceptions.ValidationException;
import com.example.decapay.models.Budget;
import com.example.decapay.models.LineItem;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.pojos.responseDtos.BudgetViewModel;
import com.example.decapay.pojos.responseDtos.LineItemRest;
import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.BudgetDtoResponse;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.LineItemRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.DateParser;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.decapay.pojos.requestDtos.BudgetDto.mapBudgetDtoToBudget;

@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final LineItemRepository lineItemRepository;
    private final UserService userService;
    private final UserUtil userUtil;



    @Override
    public List<BudgetViewModel> getBudgets(int page, int limit) {
        String email = userUtil.getAuthenticatedUserEmail();
        User user = userService.getUserByEmail(email);

        if (page > 0) page = page - 1;

        Pageable pageable = PageRequest.of(page, limit);
        Page<Budget> budgetPage = budgetRepository.findAllByUser(user, pageable);

        List<Budget> budgets = budgetPage.getContent();

        return getBudgetRest(budgets);
    }

    private List<BudgetViewModel> getBudgetRest(List<Budget> budgets) {
        List<BudgetViewModel> budgetViewModelList = new ArrayList<>();

        budgets.forEach(budget -> {
            BudgetViewModel budgetViewModel = new BudgetViewModel();
            budgetViewModel.setAmount(budget.getAmount());

            List<LineItem> lineItems = lineItemRepository.findAllByBudget(budget);
            BigDecimal totalAmountSpent = lineItems.stream()
                    .map(LineItem::getTotalAmountSpent)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            budgetViewModel.setTotalAmountSpent(totalAmountSpent);

            BigDecimal percentage = totalAmountSpent.divide(budget.getAmount(), new MathContext(2));
            budgetViewModel.setPercentage(percentage);
            budgetViewModel.setLineItemRests(getLineItemRest(lineItems));
            budgetViewModelList.add(budgetViewModel);
        });

        return budgetViewModelList;
    }

    private List<LineItemRest> getLineItemRest(List<LineItem> lineItems) {
        List<LineItemRest> lineItemRests = new ArrayList<>();

        lineItems.forEach(lineItem -> {
            LineItemRest lineItemRest = new LineItemRest();

            BigDecimal totalAmountSoFar = lineItem.getTotalAmountSpent();
            BigDecimal percentageSoFar = totalAmountSoFar.divide(lineItem.getProjectedAmount(), new MathContext(2));
            lineItemRest.setProjectedAmount(lineItem.getProjectedAmount());
            lineItemRest.setAmountSpentSoFar(totalAmountSoFar);
            lineItemRest.setPercentageSpentSoFar(percentageSoFar);
            lineItemRests.add(lineItemRest);
        });

        return lineItemRests;
    }

    public CreateBudgetResponse createBudget(CreateBudgetRequest budgetRequest) {
        String email = userUtil.getAuthenticatedUserEmail();

        User activeUser = userService.getUserByEmail(email);

        Budget budget = CreateBudgetRequest.mapCreateBudgetRequestToBudget(budgetRequest);

        saveBudget(budget, activeUser);

        return CreateBudgetResponse.convertBudgetToCreateBudgetResponse(budget);
    }

    @Override
    public CreateBudgetResponse fetchBudgetById(Long budgetId) {
        String email = userUtil.getAuthenticatedUserEmail();

        User activeUser = userService.getUserByEmail(email);

        Budget budget = budgetRepository.findBudgetByIdAndUserId(budgetId, activeUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.BAD_REQUEST, "Budget with id: " + budgetId + " Not Found"
                ));
        return CreateBudgetResponse.convertBudgetToCreateBudgetResponse(budget);
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

    @Override
    public BudgetDtoResponse updateBudget(BudgetDto budgetDto, Long budgetId) {

        String email = userUtil.getAuthenticatedUserEmail();
        User user = userService.getUserByEmail(email);

        Budget budget = getBudget(budgetId);

        budget.setTitle(budgetDto.getTitle());
        budget.setAmount(budgetDto.getAmount());
        budget.setDescription(budgetDto.getDescription());

        mapBudgetDtoToBudget(budgetDto, budget);
        saveBudget(budget, user);
        return BudgetDtoResponse.convertBudgetToBudgetDtoResponse(budget);

    }

    private Budget getBudget(Long budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.BAD_REQUEST, "Budget with id: " + budgetId + " Not Found"));
    }

    private void saveBudget(Budget budget, User user) {
        budget.setUser(user);
        budgetRepository.save(budget);
    }




}