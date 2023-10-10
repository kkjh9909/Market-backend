package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.service.NeighborPostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/neighbor/post/like")
public class NeighborPostLikeController {

    private final NeighborPostLikeService neighborPostLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> addLike(@PathVariable long postId) {
        Response response = neighborPostLikeService.likePost(postId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteLike(@PathVariable long postId) {
        Response response = neighborPostLikeService.dislikePost(postId);

        return ResponseEntity.ok(response);
    }
}
