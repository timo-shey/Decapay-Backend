package com.example.decapay.services.impl;
import com.example.decapay.exceptions.AuthenticationException;
import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.models.Budget;
import com.example.decapay.models.User;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Override
    public void deleteBudget(Long budget_id) {

        String email = userUtil.getAuthenticatedUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        HttpStatus.BAD_REQUEST, "User with email: " + email + " Not Found"));

        Budget budget = budgetRepository.findById(budget_id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.BAD_REQUEST, "Budget with id: " + budget_id + " Not Found"));

        boolean alreadyDeleted = budget.isDeleted();

        if (alreadyDeleted) {
            throw new ResourceNotFoundException(
                    HttpStatus.BAD_REQUEST, "Budget with id: " + budget_id + " Already Deleted");
        }

        boolean authorized = budget.getUser().getId().equals(user.getId());

        if (!authorized){
            throw new AuthenticationException("Action Not Authorized");
        }

        budget.setDeleted(true);
        budgetRepository.save(budget);
    }
}