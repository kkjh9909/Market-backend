package com.example.marketbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue
    private long id;

    private String lastMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductPost post;

    public ChatRoom() {}

    public ChatRoom(User user1, User user2, ProductPost post) {
        this.user1 = user1;
        this.user2 = user2;
        this.post = post;
    }
}
