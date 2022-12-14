package com.example.decapay.services.impl;

import com.example.decapay.exceptions.EntityNotFoundException;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.ResponseManager;
import com.example.decapay.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private UserUtil userUtil;

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
