package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostDTO;
import com.example.marketbackend.dto.post.vo.ProductPostListDTO;
import com.example.marketbackend.dto.post.vo.UserInfoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductPostsGetResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("count")
    private long count;

    @JsonProperty("post_info")
    private List<ProductPostListDTO> productPostListDTO;

    public ProductPostsGetResponse(String message, long count, List<ProductPostListDTO> productPostListDTO) {
        this.message = message;
        this.count = count;
        this.productPostListDTO = productPostListDTO;
    }
}
