package com.example.decapay.pojos.requestDtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
