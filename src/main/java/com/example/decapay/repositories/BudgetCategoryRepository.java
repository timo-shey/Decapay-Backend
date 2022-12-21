package com.example.decapay.repositories;

import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,Long> {
    List<BudgetCategory> findByUser(User user);
}
