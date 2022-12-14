package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;

import com.example.decapay.pojos.requestDtos.LoginRequestDto;

import com.example.decapay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.utils.ResponseManager;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ResponseManager responseManager;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, ResponseManager responseManager) {
        this.userRepository = userRepository;
        this.responseManager = responseManager;
    }

    @Override
    public ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto) {
         authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        final UserDetails user = customUserDetailService.loadUserByUsername(loginRequestDto.getEmail());

        if (user != null)
            return ResponseEntity.ok(jwtUtils.generateToken(user));

        return ResponseEntity.status(400).body("Some Error Occurred");
    }

    @Override
    @Transactional
    public ApiResponse<String> editUser(Long Id, UserUpdateRequest userUpdateRequest){

            User user = userRepository.findById(Id).orElse(null);
            if (user == null)
                return responseManager.error("User not found");

            user.setFirstName(userUpdateRequest.getFirstname());
            user.setLastName(userUpdateRequest.getLastname());
            user.setEmail(userUpdateRequest.getEmail());
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());

            userRepository.save(user);

            return responseManager.success("User details updated");
        }
}
