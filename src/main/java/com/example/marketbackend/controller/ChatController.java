package com.example.marketbackend.controller;

import com.example.marketbackend.dto.chat.request.ChatRequest;
import com.example.marketbackend.dto.chat.response.ChatListResponse;
import com.example.marketbackend.dto.chat.response.ChatRoomNumResponse;
import com.example.marketbackend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final Map<String, Integer> sessions = new ConcurrentHashMap<>();

    @GetMapping("/api/chatroom")
    public ResponseEntity<?> getChatRoom(@RequestParam long postId, @RequestParam long receiverId) {
        ChatRoomNumResponse response = chatService.getChatRoomNum(postId, receiverId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/chats/{roomId}")
    public ResponseEntity<?> getChatList(@PathVariable long roomId, Pageable pageable) {
        ChatListResponse response = chatService.getChatList(roomId, pageable);

        return ResponseEntity.ok(response);
    }

    @MessageMapping("/chat/{chatroom}")
    public void handleChatMessage(@DestinationVariable long chatroom, ChatRequest message, SimpMessageHeaderAccessor accessor) {
        System.out.println(message.toString());
        System.out.println(chatroom);

        Integer senderId = sessions.get(accessor.getSessionId());

        chatService.sendMessage(chatroom, message, senderId);
    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event){
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        String userId = event.getMessage().getHeaders().get("nativeHeaders").toString().split("User=\\[")[1].split("]")[0];

        sessions.put(sessionId, Integer.valueOf(userId));
    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event) {
        sessions.remove(event.getSessionId());
    }
}
