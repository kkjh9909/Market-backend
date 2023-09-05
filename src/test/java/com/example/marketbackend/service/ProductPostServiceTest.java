package com.example.marketbackend.service;

import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.repository.ProductPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductPostServiceTest {

    @Autowired
    private ProductPostService productPostService;

    @Autowired
    private ProductPostRepository productPostRepository;


    @Test
    @DisplayName("판매글 작성 테스트")
    public void write() {
        ProductPostWriteRequest request = new ProductPostWriteRequest("title", 10_000, "content", false);

        productPostService.write(request);

    }

}