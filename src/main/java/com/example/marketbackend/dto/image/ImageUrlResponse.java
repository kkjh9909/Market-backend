package com.example.marketbackend.dto.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageUrlResponse {

    @JsonProperty("image_url")
    private String imageUrl;

    public ImageUrlResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
