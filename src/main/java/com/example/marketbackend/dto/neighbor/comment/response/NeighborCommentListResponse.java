package com.example.marketbackend.dto.neighbor.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NeighborCommentListResponse {

    @JsonProperty("count")
    private long count;

    @JsonProperty("comments")
    private List<NeighborCommentResponse> commentResponses;
}
