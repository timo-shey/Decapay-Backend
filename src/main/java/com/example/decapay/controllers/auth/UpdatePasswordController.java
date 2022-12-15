package com.example.decapay.controllers.auth;


import com.example.decapay.pojos.requestDtos.PasswordUpdateRequest;
import com.example.decapay.pojos.responseDtos.ApiResponse;
import com.example.decapay.services.PasswordUpdateService;
import com.example.decapay.utils.ResponseManager;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class UpdatePasswordController {
    private final PasswordUpdateService passwordUpdateService;
    private final ResponseManager responseManager;

    @PostMapping("/update-password")
    public ApiResponse<String> updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        this.passwordUpdateService.updatePassword(passwordUpdateRequest);
        return responseManager.success("password updated successfully!");
    }
}
