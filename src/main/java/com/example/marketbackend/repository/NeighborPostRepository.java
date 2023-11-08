package com.example.marketbackend.repository;

import com.example.marketbackend.entity.NeighborPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NeighborPostRepository extends JpaRepository<NeighborPost, Long> {

    Page<NeighborPost> findByIsDeletedFalse(Pageable pageable);
    Page<NeighborPost> findByCategoryAndIsDeletedFalse(String category, Pageable pageable);

    @Modifying
    @Query("update NeighborPost p set p.hits = p.hits + 1 where p.id = :id")
    void increaseHits(@Param("id") long id);

    Page<NeighborPost> findByUserId(long userId, Pageable pageable);

    Page<NeighborPost> findByIsDeletedFalseAndLikesGreaterThan(int likes, Pageable pageable);
}
