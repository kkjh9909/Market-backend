package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByUser1IdAndUser2IdAndPostId(long user1Id, long user2Id, long postId);

    Page<ChatRoom> findByUser1IdOrUser2Id(long user1Id, long user2Id, Pageable pageable);
}
