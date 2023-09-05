package com.example.marketbackend.repository;

import com.example.marketbackend.entity.ProductPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPostRepository extends JpaRepository<ProductPost, Long> {


}
