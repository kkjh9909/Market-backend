package com.example.marketbackend.dto.favorite.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteDeleteResponse {

    @JsonProperty("favorite_count")
    private int favoriteCount;
}
