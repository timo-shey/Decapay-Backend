package com.example.decapay.pojos.responseDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String firstName;
    private String lastName;
    private String email;
}
