package com.example.marketbackend.dto.neighbor.post.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeighborPostDetailResponse {

    @JsonProperty("post_info")
    private NeighborPostDetail postDetail;

    @JsonProperty("user_info")
    private NeighborPostUser userDetail;
}
