package com.example.decapay.controllers;

import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid LoginRequestDto loginRequestDto) throws UserNotFoundException {
        return userService.userLogin(loginRequestDto);
    }

}
