package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.PasswordUpdateRequest;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.PasswordUpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordUpdateServiceImplTest {
    @Autowired
    private PasswordUpdateService passwordUpdateService;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        passwordUpdateService = new PasswordUpdateServiceImpl(jwtUtils, userRepository, passwordEncoder);
    }

    @Test
    void createPassword() {
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(
                "1234", "123456", "123456", "hvsailweoaukhqhadghivakuxaseip");
        User user = new User();
        user.setPassword("1234");
        user.setEmail("jon@email.com");

        given(jwtUtils.extractUsername(passwordUpdateRequest.getToken())).willReturn("jon@email.com");

        given(userRepository.findByEmail("jon@email.com")).willReturn(Optional.of(user));

        given(passwordEncoder.matches(passwordUpdateRequest.getPassword(), user.getPassword())).willReturn(true);

        passwordUpdateService.createPassword(passwordUpdateRequest);

        verify(userRepository).save(any());
    }
}