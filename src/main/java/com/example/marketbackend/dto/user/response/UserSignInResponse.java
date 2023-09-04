package com.example.marketbackend.dto.user.response;

import lombok.Data;

@Data
public class UserSignInResponse {

    private String message;

    public UserSignInResponse(String message) {
        this.message = message;
    }
}
