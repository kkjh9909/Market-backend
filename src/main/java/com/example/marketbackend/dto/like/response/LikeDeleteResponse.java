package com.example.marketbackend.dto.like.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeDeleteResponse {

    @JsonProperty("like_count")
    private int likeCount;
}
