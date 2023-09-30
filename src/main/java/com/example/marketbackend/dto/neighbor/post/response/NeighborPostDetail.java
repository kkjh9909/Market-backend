package com.example.marketbackend.dto.neighbor.post.response;

import com.example.marketbackend.entity.NeighborPhoto;
import com.example.marketbackend.entity.NeighborPost;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class NeighborPostDetail {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("category")
    private String category;

    @JsonProperty("created_time")
    private LocalDateTime createdAt;

    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("like_count")
    private int likes;

    @JsonProperty("hit_count")
    private int hits;

    public static NeighborPostDetail makeNeighborPostDetail(NeighborPost post) {
        return NeighborPostDetail.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .images(post.getPhotos().stream().map(NeighborPhoto::getUrl).collect(Collectors.toList()))
                .likes(post.getLikes())
                .hits(post.getHits())
                .build();
    }
}
