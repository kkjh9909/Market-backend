package com.example.marketbackend.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileEditRequest {

    private String nickname;
    private String address;
    private String image;
}
