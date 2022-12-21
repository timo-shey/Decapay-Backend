package com.example.decapay.exceptions;
<<<<<<< HEAD

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
=======
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException{
    private HttpStatus status;
    private String message;
    public UserNotFoundException(String message, HttpStatus status, String message1) {
>>>>>>> develop
        super(message);
        this.status = status;
        this.message = message1;
    }
}