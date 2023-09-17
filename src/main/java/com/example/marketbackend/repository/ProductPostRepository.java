package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ProductPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductPostRepository extends JpaRepository<ProductPost, Long> {

    Optional<ProductPost> findByIdAndIsDeletedFalse(Long postId);
    Page<ProductPost> findByAddress(String address, Pageable pageable);

    @Query("select p from ProductPost p where p.category = :category and p.isDeleted = false order by (p.favorites + p.chatroomCount) desc")
    List<ProductPost> findAllByCategory(@Param("category") String category);

    @Modifying
    @Query("update ProductPost p set p.hits = p.hits + 1 where p.id = :id")
    public int increaseHits(@Param("id") long id);

    @Modifying
    @Query("update ProductPost p set p.chatroomCount = p.chatroomCount + 1 where p.id = :id")
    public int increaseChatrooms(@Param("id") long id);
}
