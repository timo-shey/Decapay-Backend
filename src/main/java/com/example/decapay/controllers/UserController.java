package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.ForgetPasswordRequest;
import com.example.decapay.pojos.requestDtos.ResetPasswordRequest;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Service
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PutMapping("/edit")
    public ResponseEntity<String> editUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest){

        return userService.editUser(userUpdateRequest);
    }
}
