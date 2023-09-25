package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.dto.post.vo.ProductPostListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductPostsSearchResponse {

    private long count;
    private List<ProductPostListDTO> posts;
}
