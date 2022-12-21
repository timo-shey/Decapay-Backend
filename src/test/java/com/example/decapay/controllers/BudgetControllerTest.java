package com.example.decapay.controllers;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers=BudgetController.class)
@AutoConfigureMockMvc(addFilters = false)
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private BudgetService budgetService;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;


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

    @Test
    void checkIfBudgetIsUpdated() throws Exception {



        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(1L);
        budgetDto.setTitle("hello");
        budgetDto.setAmount(BigDecimal.valueOf(2321.00));
        budgetDto.setDescription("we won");


        String pass = objectMapper.writeValueAsString(budgetDto);
        mockMvc.perform(put("/api/v1/budgets/{budgetId}", 1)
                .contentType("application/json")
                .content(pass))
                .andExpect(status().isOk());

    }
}