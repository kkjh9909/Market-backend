package com.example.marketbackend.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DuplicateIdException extends RuntimeException {

    private String field;
    private String message;

    public DuplicateIdException(String field, String message) {
        super(message);
        this.field = field;
    }
}
