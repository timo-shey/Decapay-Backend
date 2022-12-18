package com.example.decapay.services;

import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.*;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.pojos.responseDtos.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface UserService {

    UserResponseDto createUser(UserRequestDto request) throws MessagingException;
    public ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto);

    ResponseEntity<String> editUser(UserUpdateRequest userUpdateRequest);

    String forgotPasswordRequest(ForgetPasswordRequest forgotPasswordRequest);

    String resetPassword(ResetPasswordRequest request);

    String verifyToken(String token);

    User getUserByEmail(String email);
}
