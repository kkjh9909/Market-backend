package com.example.marketbackend.repository;

import com.example.marketbackend.entity.NeighborPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NeighborPostLikeRepository extends JpaRepository<NeighborPostLike, Long> {

    Optional<NeighborPostLike> findByPostIdAndUserId(long postId, long userId);
}
