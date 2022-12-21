package com.example.decapay.repositories;

import com.example.decapay.models.Budget;
import com.example.decapay.models.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 18/12/2022 - 02:10
 * @project DecaPay-Java012-Backend
 */
@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {
    List<LineItem> findAllByBudget(Budget budget);
}