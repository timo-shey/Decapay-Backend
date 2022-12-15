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
import com.example.decapay.repositories.UserRepository;

import com.example.decapay.utils.UserUtil;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, UserUtil userUtil) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
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
    public ResponseEntity<String> editUser(UserUpdateRequest userUpdateRequest) {

        String email = userUtil.getAuthenticatedUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());

        userRepository.save(user);

        return ResponseEntity.ok("User details updated");
    }
}
