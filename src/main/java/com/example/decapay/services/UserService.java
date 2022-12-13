package com.example.decapay.services;

import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto) throws UserNotFoundException;
}
