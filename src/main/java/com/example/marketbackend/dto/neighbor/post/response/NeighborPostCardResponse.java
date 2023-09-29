package com.example.marketbackend.dto.neighbor.post.response;

import com.example.marketbackend.entity.NeighborPhoto;
import com.example.marketbackend.entity.NeighborPost;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class NeighborPostCardResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("category")
    private String category;

    @JsonProperty("hit_count")
    private long hits;

    @JsonProperty("like_count")
    private long likes;

    @JsonProperty("time")
    private LocalDateTime createdAt;

    public static NeighborPostCardResponse make(NeighborPost post) {
        return NeighborPostCardResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .hits(post.getHits())
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
