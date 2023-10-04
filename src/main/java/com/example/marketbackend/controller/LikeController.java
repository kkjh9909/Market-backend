package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.favorite.response.FavoriteAddResponse;
import com.example.marketbackend.dto.favorite.response.FavoriteDeleteResponse;
import com.example.marketbackend.service.FavoriteService;
import com.example.marketbackend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/neighbor/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> addLike(@PathVariable long postId) {
        Response response = likeService.likePost(postId);

        return ResponseEntity.ok(response);
    }
}
