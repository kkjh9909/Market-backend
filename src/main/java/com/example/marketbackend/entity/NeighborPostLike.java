package com.example.marketbackend.entity;

import javax.persistence.*;

@Entity
public class NeighborPostLike {

    @Id @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private NeighborPost post;
}
