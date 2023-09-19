package com.example.marketbackend.dto.post.vo;

import com.example.marketbackend.entity.ProductPost;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ProductPostListDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category")
    private String category;

    @JsonProperty("price")
    private int price;

    @JsonProperty("address")
    private String address;

    @JsonProperty("favorite_count")
    private int favorites;

    @JsonProperty("chatroom_count")
    private int chatrooms;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("created_time")
    private LocalDateTime createdAt;

    public static ProductPostListDTO from(ProductPost productPost) {
        return ProductPostListDTO.builder()
                .id(productPost.getId())
                .title(productPost.getTitle())
                .category(productPost.getCategory())
                .price(productPost.getPrice())
                .address(productPost.getAddress())
                .favorites(productPost.getFavorites())
                .chatrooms(productPost.getChatroomCount())
                .thumbnail(productPost.getThumbnail())
                .createdAt(productPost.getCreatedAt())
                .build();
    }
}
