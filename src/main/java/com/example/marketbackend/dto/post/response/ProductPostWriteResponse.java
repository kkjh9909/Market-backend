package com.example.marketbackend.dto.post.response;

import lombok.Data;

@Data
public class ProductPostWriteResponse {

    private String message;

    public ProductPostWriteResponse(String message) {
        this.message = message;
    }
}
