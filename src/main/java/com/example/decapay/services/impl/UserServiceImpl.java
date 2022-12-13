package com.example.decapay.services.impl;

import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.ResponseManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ResponseManager responseManager;

    @Override
    @Transactional
    public ApiResponse<String> editUser(Long Id, UserUpdateRequest userUpdateRequest) {

        User user = userRepository.findById(Id).orElse(null);
        if (user == null)
            return responseManager.error("User not found");

        user.setFirstname(userUpdateRequest.getFirstname());
        user.setLastname(user.getLastname());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());

        userRepository.save(user);

        return responseManager.success("User details updated");
    }
}
