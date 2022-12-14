package com.example.decapay.utils;


import com.example.decapay.pojos.responseDtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseManager<T> {
    public ApiResponse success(T data){
        ApiResponse apiResponse = new ApiResponse<T>("Success!", HttpStatus.OK.value(), data);
        return apiResponse;
    }
    public ApiResponse error(T data){
        ApiResponse apiResponse = new ApiResponse<T>("Error!", HttpStatus.NOT_FOUND.value(), data);
        return apiResponse;
    }
}
