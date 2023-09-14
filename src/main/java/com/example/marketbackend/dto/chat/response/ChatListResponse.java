package com.example.marketbackend.dto.chat.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatListResponse {

    private String message;

    private long count;

    private ChatList chatList;
}
