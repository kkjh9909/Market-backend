package com.example.marketbackend.repository;

import com.example.marketbackend.entity.NeighborPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeighborPostRepository extends JpaRepository<NeighborPost, Long> {

    Page<NeighborPost> findByIsDeletedFalse(Pageable pageable);
    Page<NeighborPost> findByCategoryAndIsDeletedFalse(String category, Pageable pageable);
}
