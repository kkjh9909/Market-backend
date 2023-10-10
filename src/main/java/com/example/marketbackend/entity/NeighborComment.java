package com.example.marketbackend.entity;

import com.example.marketbackend.dto.neighbor.comment.request.NeighborCommentWriteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @OneToMany(mappedBy = "parent")
    private Map<Long, NeighborComment> replies;

    public NeighborComment() {}

    public static NeighborComment makeComment(NeighborCommentWriteRequest request,
                                              User user, NeighborPost post,
                                              NeighborComment parent) {
        NeighborComment comment = NeighborComment.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .parent(parent)
                .build();

        comment.replies = new LinkedHashMap<>();

        return comment;
    }

    public void increaseLike() {
        this.likes++;
    }

    public void decreaseLike() {
        this.likes--;
    }
}
