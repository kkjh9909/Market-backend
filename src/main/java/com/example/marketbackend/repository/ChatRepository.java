package com.example.marketbackend.repository;

import com.example.marketbackend.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Page<Chat> findByRoomIdOrderByCreatedAtDesc(long roomId, Pageable pageable);
}
