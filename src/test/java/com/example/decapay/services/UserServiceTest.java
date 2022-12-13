package com.example.decapay.services;

import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.impl.UserServiceImpl;
import com.example.decapay.utils.ResponseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponseManager responseManager;

    private UserUpdateRequest userUpdateRequest;
    @BeforeEach
    void setUp() {

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFirstname("Michael");
        userUpdateRequest.setLastname("Ajayi");
        userUpdateRequest.setEmail("olamic695@gmail.com");
        userUpdateRequest.setPhoneNumber("08022222222");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void editUser() {

       ApiResponse response = new ApiResponse<String>(
               "Success", HttpStatus.OK.value(), "User details updated");

       User user = new User();

       given(userRepository.findById(1L)).willReturn(Optional.of(user));

       given(responseManager.success("User details updated")).willReturn(response);

       userService.editUser(1L, userUpdateRequest);

       verify(userRepository).save(user);
    }
}