package com.example.marketbackend.service;

import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.chat.request.ChatRequest;
import com.example.marketbackend.dto.chat.response.ChatList;
import com.example.marketbackend.dto.chat.response.ChatListResponse;
import com.example.marketbackend.dto.chat.response.ChatResponse;
import com.example.marketbackend.dto.chat.response.ChatRoomNumResponse;
import com.example.marketbackend.entity.Chat;
import com.example.marketbackend.entity.ChatRoom;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ChatRepository;
import com.example.marketbackend.repository.ChatRoomRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;

    public ChatRoomNumResponse getChatRoomNum(long receiverId) {
        long senderId = authenticationService.getUserId();

        long user1 = Math.min(senderId, receiverId);
        long user2 = Math.max(senderId, receiverId);

        Optional<ChatRoom> room = chatRoomRepository.findByUser1IdAndUser2Id(user1, user2);

        if(room.isPresent())
            return new ChatRoomNumResponse(ResponseMessage.CHAT_ROOM, room.get().getId(), senderId);
        else {
            Optional<User> sender = userRepository.findById(user1);
            Optional<User> receiver = userRepository.findById(user2);

            ChatRoom newRoom = new ChatRoom(sender.get(), receiver.get());
            chatRoomRepository.save(newRoom);

            return new ChatRoomNumResponse(ResponseMessage.CHAT_ROOM, newRoom.getId(), senderId);
        }
    }

    public ChatListResponse getChatList(long roomId, Pageable pageable) {
        Page<Chat> chats = chatRepository.findByRoomIdOrderByCreatedAtDesc(roomId, pageable);

        long count = chats.getTotalElements();

        List<ChatResponse> chatResponseList = chats.stream().map(ChatResponse::from).collect(Collectors.toList());

        return new ChatListResponse(ResponseMessage.CHATS_LIST, count, new ChatList(chatResponseList));
    }


}
