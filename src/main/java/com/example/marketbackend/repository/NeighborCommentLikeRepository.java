package com.example.marketbackend.repository;

import com.example.marketbackend.entity.NeighborComment;
import com.example.marketbackend.entity.NeighborCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NeighborCommentLikeRepository extends JpaRepository<NeighborCommentLike, Long> {

    Optional<NeighborCommentLike> findByCommentIdAndUserId(long commentId, long userId);
}
