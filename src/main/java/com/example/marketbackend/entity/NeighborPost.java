package com.example.marketbackend.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class NeighborPost {

    @Id @GeneratedValue
    private long id;

    private String title;

    private String content;

    private String category;

    private String address;

    private LocalDateTime createdAt;

    @Builder.Default
    private boolean isDeleted = false;

    @Builder.Default
    private int hits = 0;

    @Builder.Default
    private int favorites = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
