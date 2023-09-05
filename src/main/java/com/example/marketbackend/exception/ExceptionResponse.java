package com.example.marketbackend.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionResponse extends RuntimeException{

    @JsonProperty("result")
    private String result = "failed";

    @JsonProperty("message")
    private String message;

    @JsonProperty("error_code")
    private int errorCode;

    public ExceptionResponse(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
