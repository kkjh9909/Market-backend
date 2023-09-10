package com.example.marketbackend.entity;

import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private String address;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "productPost", fetch = FetchType.LAZY)
    private List<ProductPhoto> productPhotos = new ArrayList<>();

    public ProductPost() { }

    public static ProductPost writeProductPost(ProductPostWriteRequest request, User user) {
        ProductPost post = ProductPost.builder().title(request.getTitle())
                .content(request.getContent())
                .price(request.getPrice())
                .isDeal(request.isDeal())
                .category(request.getCategory())
                .address(user.getAddress())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        post.addPhotos(List.of(request.getImages()));
        return post;
    }

    public void addPhotos(List<String> images) {
        List<ProductPhoto> photos = images.stream()
                .map(url -> ProductPhoto.from(this, url))
                .collect(Collectors.toList());

        this.productPhotos.addAll(photos);
    }
}
