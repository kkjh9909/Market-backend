package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.chat.response.ChatRoomList;
import com.example.marketbackend.dto.chat.response.ChatRoomListResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.marketbackend.dto.chat.response.ChatRoomList.createChatRoomList;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final AuthenticationService authenticationService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ProductPostRepository productPostRepository;
    private final UserRepository userRepository;

    @Transactional
    public Response getChatRoomNum(long postId, long receiverId) {
        long senderId = authenticationService.getUserId();

        long user1 = Math.min(senderId, receiverId);
        long user2 = Math.max(senderId, receiverId);

        Optional<ChatRoom> room = chatRoomRepository.findByUser1IdAndUser2IdAndPostId(user1, user2, postId);

        if(room.isPresent())
            return new Response(ResponseMessage.CHAT_ROOM, new ChatRoomNumResponse(room.get().getId(), senderId));
        else {
            Optional<User> sender = userRepository.findById(user1);
            Optional<User> receiver = userRepository.findById(user2);

            Optional<ProductPost> post = productPostRepository.findById(postId);

            ChatRoom newRoom = new ChatRoom(sender.get(), receiver.get(), post.get());
            chatRoomRepository.save(newRoom);

            productPostRepository.increaseChatrooms(postId);

            return new Response(ResponseMessage.CHAT_ROOM, new ChatRoomNumResponse(newRoom.getId(), senderId));
        }
    }

    public Response getChatRoomList(Pageable pageable) {
        long userId = authenticationService.getUserId();

        Page<ChatRoom> chatRooms = chatRoomRepository.findByUser1IdOrUser2Id(userId, userId, pageable);

        List<ChatRoomList> list = new ArrayList<>();

        int notValidRoom = 0;

        for (ChatRoom chatRoom : chatRooms) {
            Optional<Chat> chat = chatRepository.findFirstByRoomIdOrderByCreatedAtDesc(chatRoom.getId());

            if(chat.isEmpty()) {
                notValidRoom++;
                continue;
            }

            ChatRoomList chatRoomList = createChatRoomList(chatRoom, userId, chat.get());
            list.add(chatRoomList);
        }

        long count = chatRooms.getTotalElements() - notValidRoom;

        return new Response(ResponseMessage.CHAT_ROOMS_GET, new ChatRoomListResponse(count, list));
    }
}
