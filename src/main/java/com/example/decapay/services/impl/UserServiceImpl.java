package com.example.decapay.services.impl;

import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;

public interface UserServiceImpl {
    public LoginResponseDto userLogin(LoginRequestDto loginRequestDto) throws UserNotFoundException;
}
