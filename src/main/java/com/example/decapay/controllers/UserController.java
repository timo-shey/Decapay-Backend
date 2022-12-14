package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PutMapping("/edit/{Id}")
    public ApiResponse editUser(@PathVariable Long Id, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return userService.editUser(Id, userUpdateRequest);
    }
}
