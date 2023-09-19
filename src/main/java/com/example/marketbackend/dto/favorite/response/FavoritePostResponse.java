package com.example.marketbackend.dto.favorite.response;

import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.ProductPostFavorite;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class FavoritePostResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("address")
    private String address;

    @JsonProperty("price")
    private int price;

    @JsonProperty("created_time")
    private LocalDateTime createdAt;

    @JsonProperty("favorite_count")
    private int favorites;

    @JsonProperty("chatroom_count")
    private int chatrooms;

    @JsonProperty("thumbnail")
    private String thumbnail;

    public static FavoritePostResponse createFavoritePostResponse(ProductPostFavorite favorite) {
        ProductPost post = favorite.getPost();

        return FavoritePostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .price(post.getPrice())
                .address(post.getAddress())
                .createdAt(post.getCreatedAt())
                .favorites(post.getFavorites())
                .chatrooms(post.getChatroomCount())
                .thumbnail(post.getThumbnail())
                .build();
    }
}
