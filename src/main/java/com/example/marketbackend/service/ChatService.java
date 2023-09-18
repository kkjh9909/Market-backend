package com.example.marketbackend.service;

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

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    private final ProductPostRepository productPostRepository;

    @Transactional
    public ChatRoomNumResponse getChatRoomNum(long postId, long receiverId) {
        long senderId = authenticationService.getUserId();

        long user1 = Math.min(senderId, receiverId);
        long user2 = Math.max(senderId, receiverId);

        Optional<ChatRoom> room = chatRoomRepository.findByUser1IdAndUser2IdAndPostId(user1, user2, postId);

        if(room.isPresent())
            return new ChatRoomNumResponse(ResponseMessage.CHAT_ROOM, room.get().getId(), senderId);
        else {
            Optional<User> sender = userRepository.findById(user1);
            Optional<User> receiver = userRepository.findById(user2);

            Optional<ProductPost> post = productPostRepository.findById(postId);

            ChatRoom newRoom = new ChatRoom(sender.get(), receiver.get(), post.get());
            chatRoomRepository.save(newRoom);

            productPostRepository.increaseChatrooms(postId);

            return new ChatRoomNumResponse(ResponseMessage.CHAT_ROOM, newRoom.getId(), senderId);
        }
    }

    public ChatListResponse getChatList(long roomId, Pageable pageable) {
        Page<Chat> chats = chatRepository.findByRoomIdOrderByCreatedAtDesc(roomId, pageable);

        long count = chats.getTotalElements();

        List<ChatResponse> chatResponseList = chats.stream().map(ChatResponse::from).collect(Collectors.toList());

        return new ChatListResponse(ResponseMessage.CHATS_LIST, count, new ChatList(chatResponseList));
    }

    public void sendMessage(long chatroom, ChatRequest message, int senderId) {
        System.out.println("sender " + senderId);

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
