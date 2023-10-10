package com.example.marketbackend.dto.neighbor.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeighborCommentLikeAddResponse {

    @JsonProperty("like_count")
    private long likeCount;
}
