package com.example.marketbackend.entity;

import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class ProductPost {

    @Id @GeneratedValue
    private long id;

    private String title;

    private String content;

    private int price;

    private boolean isDeal;

    @Builder.Default
    private boolean isDeleted = false;

    @Builder.Default
    private int chatroomCount = 0;

    @Builder.Default
    private int hits = 0;

    @Builder.Default
    private int favorites = 0;

    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public ProductPost() { }

    public static ProductPost writeProductPost(ProductPostWriteRequest request, User user) {
        return ProductPost.builder().title(request.getTitle())
                .content(request.getContent())
                .price(request.getPrice())
                .isDeal(request.isDeal())
                .category(request.getCategory())
                .user(user)
                .build();
    }
}
