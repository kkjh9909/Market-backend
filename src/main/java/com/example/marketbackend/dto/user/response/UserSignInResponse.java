package com.example.marketbackend.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
