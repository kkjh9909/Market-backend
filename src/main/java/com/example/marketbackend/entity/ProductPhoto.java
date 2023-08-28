package com.example.marketbackend.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductPhoto {

    @Id @GeneratedValue
    private long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post_id")
    private ProductPost productPost;
}
