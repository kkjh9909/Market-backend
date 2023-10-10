package com.example.marketbackend.entity;

import com.example.marketbackend.repository.NeighborCommentLikeRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NeighborCommentLike {

    @Id @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private NeighborComment comment;

    public static NeighborCommentLike makeNeighborCommentLike(User user, NeighborComment comment) {
        return NeighborCommentLike.builder()
                .user(user)
                .comment(comment)
                .build();
    }

    public void addLike() {
        comment.increaseLike();
    }

    public void deleteLike() {
        comment.decreaseLike();
    }
}
