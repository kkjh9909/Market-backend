package com.example.marketbackend.dto.post.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPostWriteRequest {

    private String title;
    private int price;
    private String content;
    private String category;
    private boolean isDeal;
    private String[] images;
}
