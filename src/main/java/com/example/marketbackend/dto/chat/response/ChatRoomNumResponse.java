package com.example.marketbackend.dto.chat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomNumResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("chatroom_number")
    private Long chatroomNumber;

    @JsonProperty("my_id")
    private Long myId;
}
