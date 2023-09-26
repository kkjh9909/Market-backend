package com.example.marketbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class NeighborPhoto {

    @Id @GeneratedValue
    private long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "neighbor_post_id")
    private NeighborPost post;

    public NeighborPhoto() { }

    public static NeighborPhoto from(NeighborPost post, String url) {
        return NeighborPhoto.builder()
                .post(post)
                .url(url)
                .build();
    }
}
