package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import org.springframework.http.ResponseEntity;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;

public interface UserService {
    public ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto);

    ApiResponse editUser(Long Id, UserUpdateRequest userUpdateRequest);

}
