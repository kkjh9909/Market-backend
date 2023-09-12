package com.example.marketbackend.controller;

import com.example.marketbackend.dto.post.response.FavoriteDeleteResponse;
import com.example.marketbackend.dto.post.response.FavoritePostResponse;
import com.example.marketbackend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/like")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> addFavorite(@PathVariable long postId) {
        FavoritePostResponse response = favoriteService.likePost(postId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable long postId) {
        FavoriteDeleteResponse response = favoriteService.dislikePost(postId);

        return ResponseEntity.ok(response);
    }
}
