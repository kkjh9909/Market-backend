package com.example.marketbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private Object result;
}
