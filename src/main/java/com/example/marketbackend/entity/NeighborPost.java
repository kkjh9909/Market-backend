package com.example.marketbackend.entity;

import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class NeighborPost {

    @Id @GeneratedValue
    private long id;

    @Column(length = 5000)
    private String title;

    @Column(length = 5000)
    private String content;

    private String category;

    private String address;

    private LocalDateTime createdAt;

    @Builder.Default
    private boolean isDeleted = false;

    @Builder.Default
    private int hits = 0;

    @Builder.Default
    private int likes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NeighborPhoto> photos;

    public NeighborPost() { }

    public static NeighborPost writeNeighborPost(NeighborPostWriteRequest request, User user) {
        NeighborPost post = NeighborPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .address(user.getAddress())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        post.photos = new ArrayList<>();
        post.addPhotos(List.of(request.getImages()));

        return post;
    }

    private void addPhotos(List<String> images) {
        List<NeighborPhoto> photos = images.stream()
                .map(url -> NeighborPhoto.from(this, url))
                .collect(Collectors.toList());

        this.photos.addAll(photos);
    }

    public void increaseLike() {
        this.likes++;
    }

    public void decreaseLike() {
        this.likes--;
    }
}
