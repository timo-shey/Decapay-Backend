package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.UserUpdateRequestDto;
import com.example.decapay.pojos.responseDtos.ApiResponse;

public interface UserService {
    ApiResponse editUser(Long Id, UserUpdateRequestDto userUpdateRequestDto);

}
