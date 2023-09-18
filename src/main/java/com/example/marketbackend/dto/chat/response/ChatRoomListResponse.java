package com.example.marketbackend.dto.chat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRoomListResponse {

    @JsonProperty("count")
    private long count;

    @JsonProperty("chat_rooms")
    List<ChatRoomList> chatRoomList;
}
