package com.example.marketbackend.controller;

import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.post.response.ProductPostWriteResponse;
import com.example.marketbackend.service.ProductPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/post")
public class ProductPostController {

    private final ProductPostService productPostService;

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody ProductPostWriteRequest productPostWriteRequest) {
        ProductPostWriteResponse response = productPostService.write(productPostWriteRequest);

        return ResponseEntity.ok(response);
    }
}
