package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.chat.response.ChatRoomList;
import com.example.marketbackend.dto.chat.response.ChatRoomListResponse;
import com.example.marketbackend.dto.chat.response.ChatRoomNumResponse;
import com.example.marketbackend.entity.ChatRoom;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ChatRoomRepository;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.marketbackend.dto.chat.response.ChatRoomList.createChatRoomList;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final AuthenticationService authenticationService;
    private final ChatRoomRepository chatRoomRepository;
    private final ProductPostRepository productPostRepository;
    private final UserRepository userRepository;

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

    public Response getChatRoomList(Pageable pageable) {
        long me = authenticationService.getUserId();

        Page<ChatRoom> chatRooms = chatRoomRepository.findByUser1IdOrUser2Id(me, me, pageable);

        List<ChatRoomList> chatRoomList = chatRooms.stream()
                .map(chatRoom -> createChatRoomList(chatRoom, me)).collect(Collectors.toList());

        long count = chatRooms.getTotalElements();

        return new Response(ResponseMessage.CHAT_ROOMS_GET, new ChatRoomListResponse(count, chatRoomList));
    }
}
