package com.example.marketbackend.dto.neighbor.comment.response;

import com.example.marketbackend.entity.NeighborComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class NeighborCommentResponse {

    private long id;
    private String nickname;
    private LocalDateTime createdAt;
    private int likes;
    private boolean isMine;
    private boolean isReply;
    private String content;

    public static NeighborCommentResponse makeCommentResponse(NeighborComment comment, long userId) {
        return NeighborCommentResponse.builder()
                .id(comment.getId())
                .nickname(comment.getUser().getNickname())
                .createdAt(comment.getCreatedAt())
                .likes(comment.getLikes())
                .isMine(comment.getUser().getId() == userId)
                .isReply(comment.getParent() != null)
                .content(comment.getContent())
                .build();
    }
}
