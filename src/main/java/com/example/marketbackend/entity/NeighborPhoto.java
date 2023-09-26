package com.example.marketbackend.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class NeighborPhoto {

    @Id @GeneratedValue
    private long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "neighbor_post_id")
    private NeighborPost post;
}
