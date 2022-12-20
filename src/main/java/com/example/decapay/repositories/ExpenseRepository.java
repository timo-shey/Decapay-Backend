package com.example.decapay.repositories;

import com.example.decapay.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long > {
//    List<Expense> findById();
}
