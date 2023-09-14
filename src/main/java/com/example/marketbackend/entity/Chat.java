package com.example.marketbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Chat {

    @Id @GeneratedValue
    private long id;

    private String message;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom room;

    public Chat() {}

    public Chat(String message, User sender, ChatRoom room) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.sender = sender;
        this.room = room;
    }
}
