package com.example.marketbackend.entity;

import com.example.marketbackend.dto.neighbor.comment.request.NeighborCommentWriteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class NeighborComment {

    @Id @GeneratedValue
    private long id;

    private String content;

    private LocalDateTime createdAt;

    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private NeighborComment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private NeighborPost post;

    @Builder.Default
    private int likes = 0;

    public NeighborComment() {}

    public static NeighborComment makeComment(NeighborCommentWriteRequest request,
                                              User user, NeighborPost post,
                                              NeighborComment parent) {
        return NeighborComment.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .parent(parent)
                .build();
    }
}
