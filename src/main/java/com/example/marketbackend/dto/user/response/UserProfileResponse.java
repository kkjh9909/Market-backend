package com.example.marketbackend.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileResponse {

    @JsonProperty("image")
    private String image;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("address")
    private String address;
}
