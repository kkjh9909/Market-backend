package com.example.marketbackend.controller;

import com.example.marketbackend.dto.chat.request.ChatRequest;
import com.example.marketbackend.dto.chat.response.ChatListResponse;
import com.example.marketbackend.dto.chat.response.ChatResponse;
import com.example.marketbackend.dto.chat.response.ChatRoomNumResponse;
import com.example.marketbackend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;



@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;


    @PostMapping("/api/chatroom/{receiverId}")
    public ResponseEntity<?> getChatRoom(@PathVariable long receiverId) {
        ChatRoomNumResponse response = chatService.getChatRoomNum(receiverId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/chats/{roomId}")
    public ResponseEntity<?> getChatList(@PathVariable long roomId, Pageable pageable) {
        ChatListResponse response = chatService.getChatList(roomId, pageable);

        return ResponseEntity.ok(response);
    }


}
