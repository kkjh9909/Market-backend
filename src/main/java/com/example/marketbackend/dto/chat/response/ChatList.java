package com.example.marketbackend.dto.chat.response;

import com.example.marketbackend.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ChatList {

    private List<ChatResponse> chats;

}
