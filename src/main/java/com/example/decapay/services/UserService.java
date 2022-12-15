package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import org.springframework.http.ResponseEntity;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto);

    ResponseEntity<String> editUser(UserUpdateRequest userUpdateRequest);

}
