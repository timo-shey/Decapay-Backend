package com.example.decapay.services;

import com.example.decapay.configurations.mails.EmailSenderService;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.impl.UserServiceImpl;
import com.example.decapay.utils.MailSenderUtil;
import com.example.decapay.utils.UserIdUtil;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private MailSenderUtil mailSenderUtil;
    @Mock
    private UserIdUtil userIdUtil;

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

        userService = new UserServiceImpl(
                userRepository, userUtil, passwordEncoder,
                emailSenderService, tokenRepository, mailSenderUtil, userIdUtil);

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFirstName("Mic");
        userUpdateRequest.setLastName("Aj");
        userUpdateRequest.setEmail("mic.com");
        userUpdateRequest.setPhoneNumber("08022222222");

    }

    @Test
    void editUser() {

       User user = new User();
       String email = "mic@gmail.com";

       given(userUtil.getAuthenticatedUserEmail()).willReturn(email);

       given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

       userService.editUser(userUpdateRequest);

       verify(userRepository).save(user);
    }
}