package com.example.marketbackend.dto.post.vo;

import com.example.marketbackend.entity.ProductPost;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductPostListDTO {

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

    public static ProductPostListDTO from(ProductPost productPost) {
        return ProductPostListDTO.builder()
                .title(productPost.getTitle())
                .category(productPost.getCategory())
                .price(productPost.getPrice())
                .address(productPost.getAddress())
                .favorites(productPost.getFavorites())
                .chatrooms(productPost.getChatroomCount())
                .build();
    }
}
