package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ProductPostFavorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductPostFavoriteRepository extends JpaRepository<ProductPostFavorite, Long> {

    Optional<ProductPostFavorite> findByPostIdAndUserId(Long postId, Long userId);

    @Query(value = "select f from ProductPostFavorite f join fetch f.post where f.user.id = :userId",
            countQuery = "select count(f) from ProductPostFavorite f where f.id = :userId")
    Page<ProductPostFavorite> findByUserId(@Param("userId") long userId, Pageable pageable);
}
