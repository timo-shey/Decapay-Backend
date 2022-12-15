package com.example.decapay.controllers.auth;

import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final UserServiceImpl userService;

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return userService.userLogin(loginRequestDto);
    }
}
