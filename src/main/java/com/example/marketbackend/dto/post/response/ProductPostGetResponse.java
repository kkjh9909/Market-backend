package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostDto;
import com.example.marketbackend.dto.post.vo.UserInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductPostGetResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("post_info")
    private ProductPostDto productPostDTO;

    @JsonProperty("user_info")
    private UserInfoDto userInfoDTO;

    public ProductPostGetResponse(String message, ProductPostDto productPostDTO, UserInfoDto userInfoDTO) {
        this.message = message;
        this.productPostDTO = productPostDTO;
        this.userInfoDTO = userInfoDTO;
    }
}
