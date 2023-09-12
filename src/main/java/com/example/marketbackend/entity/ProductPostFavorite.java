package com.example.marketbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class ProductPostFavorite {

    @Id @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductPost post;

    public ProductPostFavorite() {}

    public ProductPostFavorite(User user, ProductPost post) {
        this.user = user;
        this.post = post;
    }

    public void addLike() {
        post.increaseFavorite();
    }

    public void deleteLike() {
        post.decreaseFavorite();
    }
}
