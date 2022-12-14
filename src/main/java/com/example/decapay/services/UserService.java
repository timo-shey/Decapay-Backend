package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> editUser(UserUpdateRequest userUpdateRequest);

}
