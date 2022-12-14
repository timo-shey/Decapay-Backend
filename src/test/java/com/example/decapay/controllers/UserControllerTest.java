package com.example.decapay.controllers;

import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers=UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Test
    void editUser() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setFirstName("Michael");
        updateRequest.setLastName("Ajayi");
        updateRequest.setEmail("mic@gmail.com");

        User user = new User();
        BeanUtils.copyProperties(updateRequest, user);
        user.setFirstName("Mike");
        user.setLastName("Ola");
        user.setPassword("1234ee");
        user.setPhoneNumber("09890");

        given(userService.editUser(updateRequest)).willReturn(ResponseEntity.ok("User details updated"));

        String requestBody = mapper.writeValueAsString(updateRequest);
        mockMvc.perform(put("/api/v1/user/edit/")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());

    }
}