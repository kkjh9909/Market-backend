package com.example.marketbackend.dto.neighbor.comment.request;

import lombok.Data;

@Data
public class NeighborCommentWriteRequest {

    private String content;
    private Long parentId;
}
