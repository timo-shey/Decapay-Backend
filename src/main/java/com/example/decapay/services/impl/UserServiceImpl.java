package com.example.decapay.services.impl;

import com.example.decapay.configurations.mails.EmailSenderService;
import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.ForgetPasswordRequest;
import com.example.decapay.pojos.requestDtos.ResetPasswordRequest;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.PasswordUtils;
import com.example.decapay.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.InputMismatchException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${forgot.password.url:http://localhost:5432/reset-password/}")
    private String forgotPasswordUrl;

    private final UserRepository userRepository;

    private final EmailSenderService emailSenderService;

    private final JwtUtils utils;
    private final PasswordUtils passUtil;
    private final ResponseManager responseManager;

    private final PasswordEncoder passwordEncoder;


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


    @Override
    public String generateResetToken(ForgetPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        System.out.println(forgotPasswordUrl);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        CustomUserDetailService customUserDetailService = new CustomUserDetailService(userRepository);
//        UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getEmail());
//
//        JwtUtils jwtUtils = new JwtUtils();
//        String userToken = jwtUtils.generateToken(userDetails);

        String token = utils.generatePasswordResetToken(email);
        String link = String.format("%s%s", forgotPasswordUrl, token);
        emailSenderService.sendPasswordResetEmail( forgotPasswordRequest.getEmail(), "forgot Password token", link);

//        System.out.println("http://localhost:5432/reset-password/" + token);
        return "Check your email for password reset instructions";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request, String token) {
        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new InputMismatchException("Passwords do not match");

        String email = utils.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password reset successful";
    }


}
