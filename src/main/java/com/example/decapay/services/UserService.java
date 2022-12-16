package com.example.decapay.services;

import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.pojos.requestDtos.*;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.pojos.responseDtos.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {

    UserResponseDto createUser(UserRequestDto request) throws MessagingException;
    public ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto);

    ResponseEntity<String> editUser(UserUpdateRequest userUpdateRequest);


    String forgotPasswordRequest(ForgetPasswordRequest forgotPasswordRequest);

    String resetPassword(ResetPasswordRequest request);

    String verifyToken(String token);

    ResponseEntity<String> uploadProfilePicture(MultipartFile image) throws IOException, UserNotFoundException;
}
