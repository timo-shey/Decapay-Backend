package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.UserUpdateRequestDto;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Service
@AllArgsConstructor

@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PutMapping("/edit/{Id}")
    public ApiResponse editUser(@PathVariable Long Id, @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return userService.editUser(Id, userUpdateRequestDto);
    }
}
