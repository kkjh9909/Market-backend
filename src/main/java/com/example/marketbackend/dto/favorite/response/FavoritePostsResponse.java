package com.example.marketbackend.dto.favorite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FavoritePostsResponse {

    @JsonProperty("count")
    private long count;

    @JsonProperty("posts")
    List<FavoritePostResponse> favoritePostResponses;
}
