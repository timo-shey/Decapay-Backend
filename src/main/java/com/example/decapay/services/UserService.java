package com.example.decapay.services;

import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.*;
import com.example.decapay.pojos.responseDtos.UserResponseDto;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface UserService {

    UserResponseDto createUser(UserRequestDto request) throws MessagingException;
    ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto);

    ResponseEntity<String> editUser(UserUpdateRequest userUpdateRequest);

    ResponseEntity<String> forgotPasswordRequest(ForgetPasswordRequest forgotPasswordRequest);

    ResponseEntity<String> resetPassword(ResetPasswordRequest request);

    String verifyToken(String token);

    User getUserByEmail(String email);
}
