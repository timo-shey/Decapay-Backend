package com.example.decapay.services;

import com.example.decapay.configurations.mails.EmailSenderService;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.impl.UserServiceImpl;
import com.example.decapay.utils.ResponseManager;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserUtil userUtil;

    private UserUpdateRequest userUpdateRequest;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  EmailSenderService emailSenderService;
    @Mock
    private  TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {

        userService = new UserServiceImpl(userRepository, userUtil,passwordEncoder,tokenRepository,emailSenderService);

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFirstName("Michael");
        userUpdateRequest.setLastName("Ajayi");
        userUpdateRequest.setEmail("olamic695@gmail.com");
        userUpdateRequest.setPhoneNumber("08022222222");

    }

    @Test
    void editUser() {

       User user = new User();
       String email = "olamic695@gmail.com";

       given(userUtil.getAuthenticatedUserEmail()).willReturn(email);

       given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

       userService.editUser(userUpdateRequest);

       verify(userRepository).save(user);
    }
}