package com.example.marketbackend.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpResponse {

    @JsonProperty("user_id")
    private long userId;
}
