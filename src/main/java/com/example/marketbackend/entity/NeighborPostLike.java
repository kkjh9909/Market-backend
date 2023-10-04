package com.example.marketbackend.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
public class NeighborPostLike {

    @Id @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private NeighborPost post;

    public NeighborPostLike() {}

    public NeighborPostLike(User user, NeighborPost post) {
        this.user = user;
        this.post = post;
    }

    public void addLike() {
        post.increaseLike();
    }

    public void deleteLike() {
        post.decreaseLike();
    }
}
