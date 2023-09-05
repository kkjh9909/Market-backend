package com.example.marketbackend.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginExceptionResponse {

    @JsonProperty("result")
    private String result = "failed";

    @JsonProperty("error_code")
    private int errorCode = 404;

    @JsonProperty("message")
    private String message;

    public LoginExceptionResponse(String message) {
        this.message = message;
    }
}
