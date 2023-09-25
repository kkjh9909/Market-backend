package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {

    @Modifying
    @Query("delete from ProductPhoto p where p.productPost.id = :id")
    void deleteByPostId(@Param("id") long postId);
}
