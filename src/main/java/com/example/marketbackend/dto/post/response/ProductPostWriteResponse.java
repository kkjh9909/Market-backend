package com.example.marketbackend.dto.post.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPostWriteResponse {

    @JsonProperty("post_id")
    private long postId;
}
