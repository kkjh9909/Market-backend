package com.example.marketbackend.dto.neighbor.post.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeighborPostWriteResponse {

    @JsonProperty("post_id")
    private long postId;
}
