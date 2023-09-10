package com.example.marketbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class ProductPhoto {

    @Id @GeneratedValue
    private long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post_id")
    private ProductPost productPost;

    public ProductPhoto() { }

    public static ProductPhoto from(ProductPost productPost, String url) {
        return ProductPhoto.builder()
                .productPost(productPost)
                .imageUrl(url)
                .build();
    }
}
