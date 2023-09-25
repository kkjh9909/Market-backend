package com.example.marketbackend.repository.dsl;

import com.example.marketbackend.entity.ProductPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductPostCustomRepository {

    Page<ProductPost> findByKeyword(String address, String title, String content, String category, Pageable pageable);
}
