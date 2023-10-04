package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.favorite.response.FavoriteDeleteResponse;
import com.example.marketbackend.dto.favorite.response.FavoriteAddResponse;
import com.example.marketbackend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/like")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> addFavorite(@PathVariable long postId) {
        Response response = favoriteService.likePost(postId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable long postId) {
        Response response = favoriteService.dislikePost(postId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getFavoritePosts(Pageable pageable) {
        Response response = favoriteService.getFavoritePosts(pageable);

        return ResponseEntity.ok(response);
    }
}
