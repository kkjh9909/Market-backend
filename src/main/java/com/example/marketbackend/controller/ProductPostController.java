package com.example.marketbackend.controller;

import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.post.response.ProductPostGetResponse;
import com.example.marketbackend.dto.post.response.ProductPostWriteResponse;
import com.example.marketbackend.dto.post.response.ProductPostsByCategoryResponse;
import com.example.marketbackend.dto.post.response.ProductPostsGetResponse;
import com.example.marketbackend.service.ProductPostService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable long postId) {
        ProductPostGetResponse post = productPostService.getPost(postId);

        return ResponseEntity.ok(post);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPosts(@RequestParam Optional<String> address, Pageable pageable) {
        ProductPostsGetResponse posts = productPostService.getPosts(address.orElse(""), pageable);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/list/{category}")
    public ResponseEntity<?> getPostsByCategory(@PathVariable String category) {
        ProductPostsByCategoryResponse posts = productPostService.getPostsByCategory(category);

        return ResponseEntity.ok(posts);
    }
}
