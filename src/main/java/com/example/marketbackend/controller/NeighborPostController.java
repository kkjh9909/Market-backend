package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
import com.example.marketbackend.service.NeighborPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/neighbor/post")
public class NeighborPostController {

    private final NeighborPostService neighborPostService;

    @PostMapping("/write")
    public ResponseEntity<?> writePost(@RequestBody NeighborPostWriteRequest request) {
        Response response = neighborPostService.write(request);

        return ResponseEntity.ok(response);
    }
}
