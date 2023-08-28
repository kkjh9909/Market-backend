package com.example.marketbackend.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductPostFavorite {

    @Id @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductPost productPost;
}
