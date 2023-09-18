package com.example.marketbackend.dto.chat.response;

import com.example.marketbackend.entity.Chat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ChatResponse {

    @JsonProperty("sender_id")
    private long senderId;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("message")
    private String message;

    @JsonProperty("created_time")
    private LocalDateTime createdAt;

    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                .senderId(chat.getSender().getId())
                .nickname(chat.getSender().getNickname())
                .profileImage(chat.getSender().getProfileImage())
                .message(chat.getMessage())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}
