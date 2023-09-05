package com.example.marketbackend.service;

import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.post.response.ProductPostWriteResponse;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.repository.ProductPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.marketbackend.entity.ProductPost.writeProductPost;

@Service
@RequiredArgsConstructor
public class ProductPostService {

    private final ProductPostRepository productPostRepository;

    public ProductPostWriteResponse write(ProductPostWriteRequest productPostWriteRequest) {

        ProductPost productPost = writeProductPost(productPostWriteRequest);

        productPostRepository.save(productPost);

        return new ProductPostWriteResponse(ResponseMessage.POST_WRITE);
    }
}
