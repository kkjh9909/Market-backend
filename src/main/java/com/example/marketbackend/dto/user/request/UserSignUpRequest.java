package com.example.marketbackend.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class UserSignUpRequest {

    private String userId;
    private String password;
    private String username;
    private String nickname;
    private String profileImage;
    private String address;
}
