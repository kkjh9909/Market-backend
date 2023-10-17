package com.example.marketbackend.dto.neighbor.comment.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeighborCommentWriteRequest {

    private String content;
    private Long parentId;
}
