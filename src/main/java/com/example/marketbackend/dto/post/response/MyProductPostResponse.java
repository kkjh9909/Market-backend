package com.example.marketbackend.dto.post.response;

import com.example.marketbackend.entity.ProductPhoto;
import com.example.marketbackend.entity.ProductPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class MyProductPostResponse {

    private String title;
    private int price;
    private String content;
    private String category;
    private boolean isDeal;
    private List<String> images;

    public static MyProductPostResponse makeMyPostResponse(ProductPost post) {
        return MyProductPostResponse.builder()
                .title(post.getTitle())
                .price(post.getPrice())
                .content(post.getContent())
                .category(post.getCategory())
                .isDeal(post.isDeal())
                .images(post.getProductPhotos().stream().map(ProductPhoto::getImageUrl).collect(Collectors.toList()))
                .build();
    }
}
