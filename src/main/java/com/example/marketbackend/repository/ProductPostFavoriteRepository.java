package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ProductPostFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPostFavoriteRepository extends JpaRepository<ProductPostFavorite, Long> {

    public Optional<ProductPostFavorite> findByPostIdAndUserId(Long postId, Long userId);
}
