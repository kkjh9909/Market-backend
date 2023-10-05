package com.example.marketbackend.dto.neighbor.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NeighborCommentWriteResponse {

    @JsonProperty("comment")
    Object neighborCommentResponse;
}
