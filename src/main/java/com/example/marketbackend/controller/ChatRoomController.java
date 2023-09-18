package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.chat.response.ChatRoomNumResponse;
import com.example.marketbackend.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chatroom")
    public ResponseEntity<?> getChatRoom(@RequestParam long postId, @RequestParam long receiverId) {
        ChatRoomNumResponse response = chatRoomService.getChatRoomNum(postId, receiverId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/chatroom/list")
    public ResponseEntity<?> getChatRoomList(Pageable pageable) {
        Response response = chatRoomService.getChatRoomList(pageable);

        return ResponseEntity.ok(response);
    }
}
