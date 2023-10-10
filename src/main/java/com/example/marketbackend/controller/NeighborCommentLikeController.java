package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.service.NeighborCommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/neighbor/comment/like")
public class NeighborCommentLikeController {

    private final NeighborCommentLikeService neighborCommentLikeService;

    @PostMapping("/{commentId}")
    public ResponseEntity<?> addLike(@PathVariable long commentId) {
        Response response = neighborCommentLikeService.likeComment(commentId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteLike(@PathVariable long commentId) {
        Response response = neighborCommentLikeService.dislikeComment(commentId);

        return ResponseEntity.ok(response);
    }
}
