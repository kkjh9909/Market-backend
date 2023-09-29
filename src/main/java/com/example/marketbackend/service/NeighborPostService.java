package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
import com.example.marketbackend.dto.neighbor.post.response.NeighborPostCardResponse;
import com.example.marketbackend.dto.neighbor.post.response.NeighborPostsListResponse;
import com.example.marketbackend.entity.NeighborPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.NeighborPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NeighborPostService {

    private final AuthenticationService authenticationService;
    private final NeighborPostRepository neighborPostRepository;
    private final UserRepository userRepository;

    @Transactional
    public Response write(NeighborPostWriteRequest request) {
        long userId = authenticationService.getUserId();

        Optional<User> user = userRepository.findById(userId);

        NeighborPost post = NeighborPost.writeNeighborPost(request, user.get());

        neighborPostRepository.save(post);

        return new Response(ResponseMessage.NEIGHBOR_POST_WRITE, null);
    }

    public Response getPostList(Optional<String> category, Pageable pageable) {

        Page<NeighborPost> posts;

        if(category.isEmpty())
            posts = neighborPostRepository.findByIsDeletedFalse(pageable);
        else
            posts = neighborPostRepository.findByCategoryAndIsDeletedFalse(category.get(), pageable);

        long count = posts.getTotalElements();

        List<NeighborPostCardResponse> postsDto = posts.stream().map(NeighborPostCardResponse::make).collect(Collectors.toList());

        NeighborPostsListResponse res = new NeighborPostsListResponse(count, postsDto);

        return new Response(ResponseMessage.NEIGHBOR_POSTS_GET, res);
    }
}