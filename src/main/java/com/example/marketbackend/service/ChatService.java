package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.chat.request.ChatRequest;
import com.example.marketbackend.dto.chat.response.ChatList;
import com.example.marketbackend.dto.chat.response.ChatListResponse;
import com.example.marketbackend.dto.chat.response.ChatResponse;
import com.example.marketbackend.dto.chat.response.ChatRoomNumResponse;
import com.example.marketbackend.entity.Chat;
import com.example.marketbackend.entity.ChatRoom;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ChatRepository;
import com.example.marketbackend.repository.ChatRoomRepository;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;

    public Response getChatList(long roomId, Pageable pageable) {
        Page<Chat> chats = chatRepository.findByRoomIdOrderByCreatedAtDesc(roomId, pageable);

        long count = chats.getTotalElements();

        List<ChatResponse> chatResponseList = chats.stream().map(ChatResponse::from).collect(Collectors.toList());

        return new Response(ResponseMessage.CHATS_LIST, new ChatListResponse(count, new ChatList(chatResponseList)));
    }

    public void sendMessage(long chatroom, ChatRequest message, int senderId) {
        Optional<User> user = userRepository.findById((long)senderId);
        Optional<ChatRoom> room = chatRoomRepository.findById(chatroom);

        Chat chat = new Chat(message.getMessage(), user.get(), room.get());

        chatRepository.save(chat);

        long id = user.get().getId();
        String profileImage = user.get().getProfileImage();
        String nickname = user.get().getNickname();

        simpMessagingTemplate.convertAndSend("/topic/" + chatroom, new ChatResponse(id, profileImage, nickname, chat.getMessage(), chat.getCreatedAt()));
    }
}
