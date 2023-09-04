package com.example.marketbackend.dto.user.response;

import lombok.Data;

@Data
public class UserSignUpResponse {

    private String message;

    public UserSignUpResponse(String message) {
        this.message = message;
    }
}
