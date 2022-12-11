package com.example.decapay.services;

import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {

    private final UserRepository userRepository;
    @Override
    public LoginResponseDto userLogin(LoginRequestDto loginRequestDto) throws UserNotFoundException {
        User user = userRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                    .orElseThrow(()-> new UserNotFoundException("Invalid Credentials"));

        LoginResponseDto responseDto = LoginResponseDto.builder()
                                        .firstName(user.getFirstname())
                                        .lastName(user.getLastname())
                                        .email(user.getEmail()).build();
        return responseDto;
    }
}
