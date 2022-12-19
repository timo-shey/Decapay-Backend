package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers=BudgetController.class)
@AutoConfigureMockMvc(addFilters = false)
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void testCreateBudget() throws Exception {

        CreateBudgetRequest budgetRequest = new CreateBudgetRequest();

        String budgetJson = objectMapper.writeValueAsString(budgetRequest);

        mockMvc.perform(post("/api/v1/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(budgetJson))
                        .andExpect(status().isCreated());
    }

    @Test
    void testFetchBudget() throws Exception {

        long budgetId = 2L;

        mockMvc.perform(get("/api/v1/budgets/{budgetId}", budgetId))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBudget() throws Exception {

        long budgetId = 1L;

        mockMvc.perform(delete("/api/v1/budgets/{budgetId}", budgetId))
                .andExpect(status().isOk());
    }
}