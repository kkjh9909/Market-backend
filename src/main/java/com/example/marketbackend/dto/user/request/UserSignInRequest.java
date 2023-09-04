package com.example.marketbackend.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInRequest {

    private String userId;
    private String password;
}
