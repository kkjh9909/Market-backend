package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ProductPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPostRepository extends JpaRepository<ProductPost, Long> {

    Optional<ProductPost> findByIdAndIsDeletedFalse(Long postId);

    Page<ProductPost> findByAddress(String address, Pageable pageable);
}
