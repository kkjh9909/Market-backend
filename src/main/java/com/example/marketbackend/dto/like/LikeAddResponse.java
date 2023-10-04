package com.example.marketbackend.dto.like;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeAddResponse {

    @JsonProperty("like_count")
    private int likeCount;
}
