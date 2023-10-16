package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostListDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductPostsByCategoryResponse {

    @JsonProperty("count")
    private long count;

    @JsonProperty("posts")
    private List<ProductPostListDTO> productPostListDTO;
}
