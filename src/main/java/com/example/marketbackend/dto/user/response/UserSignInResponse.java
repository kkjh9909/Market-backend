package com.example.marketbackend.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserSignInResponse {

    @JsonProperty("result")
    private String result = "success";

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("message")
    private String message;

    public UserSignInResponse(String message, String accessToken) {
        this.accessToken = accessToken;
        this.message = message;
    }
}
