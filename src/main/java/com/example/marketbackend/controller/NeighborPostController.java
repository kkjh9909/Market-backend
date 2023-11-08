package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
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
@RequestMapping("/api/neighbor/post")
public class NeighborPostController {

    private final NeighborPostService neighborPostService;

    @PostMapping("/write")
    public ResponseEntity<?> writePost(@RequestBody NeighborPostWriteRequest request) {
        Response response = neighborPostService.write(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPostList(@RequestParam Optional<String> category,
                                         @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Response response = neighborPostService.getPostList(category, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostDetail(@PathVariable long postId) {
        Response response = neighborPostService.getPostDetail(postId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyPosts(Pageable pageable) {
        Response response = neighborPostService.getMyPosts(pageable);

        return ResponseEntity.ok(response);
    }
}
