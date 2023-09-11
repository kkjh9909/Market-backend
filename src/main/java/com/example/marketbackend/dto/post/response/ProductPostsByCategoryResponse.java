package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostListDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductPostsByCategoryResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("count")
    private long count;

    @JsonProperty("post_info")
    private List<ProductPostListDTO> productPostListDTO;

    public ProductPostsByCategoryResponse(String message, long count, List<ProductPostListDTO> productPostListDTO) {
        this.message = message;
        this.count = count;
        this.productPostListDTO = productPostListDTO;
    }
}
