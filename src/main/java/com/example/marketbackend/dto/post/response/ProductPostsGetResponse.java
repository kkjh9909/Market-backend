package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostListDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductPostsGetResponse {

    @JsonProperty("count")
    private long count;

    @JsonProperty("posts")
    private List<ProductPostListDTO> productPostListDTO;

    public ProductPostsGetResponse(long count, List<ProductPostListDTO> productPostListDTO) {
        this.count = count;
        this.productPostListDTO = productPostListDTO;
    }
}
