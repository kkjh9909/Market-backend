package com.example.marketbackend.dto.post.vo;

import com.example.marketbackend.entity.ProductPhoto;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.ProductPostFavorite;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class ProductPostDTO {

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("category")
    private String category;

    @JsonProperty("hit_count")
    private int hits;

    @JsonProperty("favorite_count")
    private int favorites;

    @JsonProperty("chatroom_count")
    private int chatroom;

    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("my_post")
    private boolean isMine;

    @JsonProperty("created_time")
    private LocalDateTime createdTime;

    @JsonProperty("is_like")
    private boolean isLike;

    @JsonProperty("price")
    private int price;

    public static ProductPostDTO from(ProductPost post, long userId, ProductPostFavorite like) {
        return ProductPostDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .hits(post.getHits())
                .price(post.getPrice())
                .favorites(post.getFavorites())
                .chatroom(post.getChatroomCount())
                .isLike(like != null)
                .images(post.getProductPhotos().stream().map(ProductPhoto::getImageUrl).collect(Collectors.toList()))
                .isMine(post.getUser().getId() == userId)
                .createdTime(post.getCreatedAt())
                .build();
    }
}
