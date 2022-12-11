package com.example.decapay.pojos.requestDtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter

public class UserUpdateRequestDto {
    @NotNull(message = "First Name field can't be empty")
    private String firstname;
    @NotNull(message = "Last Name field can't be empty")
    private String lastname;
    @NotNull(message ="Email field can't be empty")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotNull(message="Phone Number field can't be empty")
    private String phoneNumber;

}
