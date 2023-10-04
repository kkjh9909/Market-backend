package com.example.marketbackend.dto.chat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatListResponse {

    @JsonProperty("count")
    private long count;

    @JsonProperty("messages")
    private ChatList chatList;
}
