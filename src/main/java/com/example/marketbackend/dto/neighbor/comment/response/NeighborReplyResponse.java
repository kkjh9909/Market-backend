package com.example.marketbackend.dto.neighbor.comment.response;

import com.example.marketbackend.entity.NeighborComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class NeighborReplyResponse {

    private long id;
    private String content;
    private String nickname;
    private String profile;

    @JsonProperty("created_time")
    private LocalDateTime createdAt;

    @JsonProperty("like_count")
    private int likes;

    @JsonProperty("my_comment")
    private boolean isMine;

    @JsonProperty("is_like")
    private boolean isLike;

    public static NeighborReplyResponse makeReplyResponse(NeighborComment reply, boolean isMine, boolean isLike) {
        return NeighborReplyResponse.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .profile(reply.getUser().getProfileImage())
                .nickname(reply.getUser().getNickname())
                .createdAt(reply.getCreatedAt())
                .likes(reply.getLikes())
                .isMine(isMine)
                .isLike(isLike)
                .build();
    }
}
