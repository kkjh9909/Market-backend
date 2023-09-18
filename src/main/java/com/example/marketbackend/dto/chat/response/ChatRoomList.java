package com.example.marketbackend.dto.chat.response;

import com.example.marketbackend.entity.ChatRoom;
import com.example.marketbackend.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomList {

    @JsonProperty("id")
    private long id;

    @JsonProperty("last_message")
    private String lastMessage;

    @JsonProperty("other_nickname")
    private String otherNickname;

    @JsonProperty("other_profile")
    private String otherImage;

    @JsonProperty("other_address")
    private String otherAddress;

    public static ChatRoomList createChatRoomList(ChatRoom room, long me) {
        User other = room.getUser1().getId() == me ?
                room.getUser2() : room.getUser1();

        return ChatRoomList.builder()
                .id(room.getId())
                .lastMessage(room.getLastMessage())
                .otherImage(other.getProfileImage())
                .otherNickname(other.getNickname())
                .otherAddress(other.getAddress())
                .build();
    }
}
