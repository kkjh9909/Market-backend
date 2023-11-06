package com.example.marketbackend.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExceptionResponse {

    @JsonProperty("field")
    private String field;

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private int errorCode;

    public ExceptionResponse(String field, String message, int errorCode) {
        this.field = field;
        this.message = message;
        this.errorCode = errorCode;
    }
}
