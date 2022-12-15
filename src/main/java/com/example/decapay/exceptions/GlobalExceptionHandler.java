package com.example.decapay.exceptions;


import com.example.decapay.pojos.responseDtos.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;



@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongPasswordException.class)
    @ResponseBody
    public ResponseEntity<String> handleWrongPasswordException(WrongPasswordException wrongPasswordException){
        return new ResponseEntity<>(wrongPasswordException.getMessage(), null, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleBusiness(UserNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(),null, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getMessage(),null, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> entityNotFoundExceptionHandler(EntityNotFoundException ex){
        return  new ApiResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND.value(),null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        return new ResponseEntity<>(resourceNotFoundException.getMessage(), null, HttpStatus.NOT_FOUND.value());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JwtException.class)
    @ResponseBody
    public ResponseEntity<String> handleJwtException(JwtException resourceNotFoundException){
        return new ResponseEntity<>(resourceNotFoundException.getMessage(), null, HttpStatus.BAD_REQUEST.value());
    }

}
