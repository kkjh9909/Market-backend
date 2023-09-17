package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    public Optional<ChatRoom> findByUser1IdAndUser2IdAndPostId(long user1Id, long user2Id, long postId);
}
