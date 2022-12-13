package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.exceptions.AuthenticationException;
import com.example.decapay.exceptions.UserNotFoundException;

import com.example.decapay.pojos.requestDtos.LoginRequestDto;

import com.example.decapay.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<String> userLogin(LoginRequestDto loginRequestDto) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword()));
                final UserDetails user = customUserDetailService.loadUserByUsername(loginRequestDto.getEmail());

                if(user != null)
                    return ResponseEntity.ok(jwtUtils.generateToken(user));

                return ResponseEntity.status(400).body("Some Error Occurred");
    }
}
