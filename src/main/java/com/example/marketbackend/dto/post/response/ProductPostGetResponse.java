package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostDto;
import com.example.marketbackend.dto.post.vo.UserInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPostGetResponse {

    @JsonProperty("post_info")
    private ProductPostDto productPostDTO;

    @JsonProperty("user_info")
    private UserInfoDto userInfoDTO;
}
