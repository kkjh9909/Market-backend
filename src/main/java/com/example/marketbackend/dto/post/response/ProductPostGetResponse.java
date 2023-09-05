package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostDTO;
import com.example.marketbackend.dto.post.vo.UserInfoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductPostGetResponse {

    @JsonProperty("result")
    private String result = "success";

    @JsonProperty("message")
    private String message;

    @JsonProperty("post_info")
    private ProductPostDTO productPostDTO;

    @JsonProperty("user_info")
    private UserInfoDTO userInfoDTO;

    public ProductPostGetResponse(String message, ProductPostDTO productPostDTO, UserInfoDTO userInfoDTO) {
        this.message = message;
        this.productPostDTO = productPostDTO;
        this.userInfoDTO = userInfoDTO;
    }
}
