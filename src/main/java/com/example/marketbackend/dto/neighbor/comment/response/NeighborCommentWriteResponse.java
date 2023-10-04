package com.example.marketbackend.dto.neighbor.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeighborCommentWriteResponse {

    @JsonProperty("comment_id")
    private long commentId;
}
