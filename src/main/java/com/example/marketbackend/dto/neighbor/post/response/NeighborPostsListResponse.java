package com.example.marketbackend.dto.neighbor.post.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NeighborPostsListResponse {

    @JsonProperty("count")
    private long count;

    @JsonProperty("posts")
    private List<NeighborPostCardResponse> posts;
}
