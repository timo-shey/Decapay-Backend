package com.example.decapay.controllers;

import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> signIn(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException {
        return new ResponseEntity<>(userService.userLogin(loginRequestDto), HttpStatus.CREATED);
    }

}
