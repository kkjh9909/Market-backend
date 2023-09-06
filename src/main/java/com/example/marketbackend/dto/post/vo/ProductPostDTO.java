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

    @JsonProperty("my_post")
    private boolean isMine;

    @JsonProperty("created_time")
    private LocalDateTime createdTime;

    public static ProductPostDTO from(ProductPost productPost, long userId) {
        return ProductPostDTO.builder()
                .title(productPost.getTitle())
                .content(productPost.getContent())
                .category(productPost.getCategory())
                .hits(productPost.getHits())
                .favorites(productPost.getFavorites())
                .chatroom(productPost.getChatroomCount())
                .isMine(productPost.getUser().getId() == userId)
                .createdTime(productPost.getCreatedAt())
                .build();
    }
}
