package com.example.decapay.services.impl;

import com.example.decapay.configurations.mails.EmailSenderService;
import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.enums.Status;
import com.example.decapay.enums.VerificationType;
import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.models.Token;
import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;

import com.example.decapay.pojos.requestDtos.LoginRequestDto;

import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.ForgetPasswordRequest;
import com.example.decapay.pojos.requestDtos.ResetPasswordRequest;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.repositories.UserRepository;

import com.example.decapay.utils.UserUtil;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.InputMismatchException;

@Service
public class UserServiceImpl implements UserService {
    @Value("${forgot.password.url:http://localhost:5432/reset-password/}")
    private String forgotPasswordUrl;

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final TokenRepository tokenRepository;


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, UserUtil userUtil,PasswordEncoder passwordEncoder,TokenRepository tokenRepository,EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.passwordEncoder=passwordEncoder;
        this.tokenRepository=tokenRepository;
        this.emailSenderService=emailSenderService;
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


    @Override
    public String forgotPasswordRequest(ForgetPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        System.out.println(forgotPasswordUrl);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String generatedToken = jwtUtils.generatePasswordResetToken(email);

        Token token = new Token();
        token.setToken(generatedToken);
        token.setStatus(Status.ACTIVE);
        token.setVerificationType(VerificationType.RESET_PASSWORD);
        token.setUser(user);

        tokenRepository.save(token);

        String link = String.format("%s%s", forgotPasswordUrl, generatedToken + " expires in 15 minutes.");
        emailSenderService.sendPasswordResetEmail( forgotPasswordRequest.getEmail(), "forgot Password token", link);
        return "Check your email for password reset instructions";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request, String token) {
        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new InputMismatchException("Passwords do not match");

        String email = jwtUtils.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Token tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Token does not exist."));

        if (tokenEntity.getStatus().equals(Status.EXPIRED))
            throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Token expired.");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        tokenEntity.setStatus(Status.EXPIRED);
        tokenRepository.save(tokenEntity);

        return "Password reset successful";
    }


}
