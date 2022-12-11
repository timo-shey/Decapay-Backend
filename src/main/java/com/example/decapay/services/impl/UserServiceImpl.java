package com.example.decapay.services.impl;

import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequestDto;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.ResponseManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ResponseManager responseManager;

    @Override
    public ApiResponse<String> editUser(Long Id, UserUpdateRequestDto userUpdateRequestDto) {

        User user = userRepository.findById(Id).orElse(null);
        if (user == null)
            return responseManager.error("User not found");

        user.setFirstname(userUpdateRequestDto.getFirstname());
        user.setLastname(user.getLastname());
        user.setEmail(userUpdateRequestDto.getEmail());
        user.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());

        userRepository.save(user);

        return responseManager.success("User details updated successfully");
    }
}
