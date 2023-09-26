package com.example.marketbackend.dto.neighbor.post.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeighborPostWriteRequest {

    private String title;
    private String content;
    private String category;
    private String[] images;
}


