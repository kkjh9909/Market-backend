package com.example.marketbackend.dto.neighbor.comment.response;

import com.example.marketbackend.entity.NeighborComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class NeighborCommentResponse {

    private long id;
    private String nickname;
    private String profile;

    @JsonProperty("created_time")
    private LocalDateTime createdAt;

    @JsonProperty("like_count")
    private int likes;

    @JsonProperty("my_comment")
    private boolean isMine;

    private String content;
    private List<NeighborReplyResponse> replies;

    public static NeighborCommentResponse makeCommentResponse(NeighborComment comment, long userId) {
        return NeighborCommentResponse.builder()
                .id(comment.getId())
                .nickname(comment.getUser().getNickname())
                .profile(comment.getUser().getProfileImage())
                .createdAt(comment.getCreatedAt())
                .likes(comment.getLikes())
                .isMine(comment.getUser().getId() == userId)
                .content(comment.getContent())
                .replies(comment.getReplies().values().stream().map(reply -> NeighborReplyResponse.makeReplyResponse(reply, userId)).collect(Collectors.toList()))
                .build();
    }
}
