package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.ForgetPasswordRequest;
import com.example.decapay.pojos.requestDtos.ResetPasswordRequest;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;

public interface UserService {
    ApiResponse editUser(Long Id, UserUpdateRequest userUpdateRequest);


    String generateResetToken(ForgetPasswordRequest forgotPasswordRequest);

    String resetPassword(ResetPasswordRequest request, String token);
}
