package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.neighbor.comment.request.NeighborCommentWriteRequest;
import com.example.marketbackend.entity.NeighborComment;
import com.example.marketbackend.service.NeighborCommentService;
import com.example.marketbackend.service.NeighborPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/neighbor/comment")
public class NeighborCommentController {

    private final NeighborCommentService neighborCommentService;

    @PostMapping("/{postId}/write")
    public ResponseEntity<?> getPostList(@PathVariable long postId,
                                         @RequestBody NeighborCommentWriteRequest request) {
        Response response = neighborCommentService.write(postId, request);

        return ResponseEntity.ok(response);
    }
}
