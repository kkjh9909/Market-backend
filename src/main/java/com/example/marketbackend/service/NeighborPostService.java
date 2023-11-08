package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
import com.example.marketbackend.dto.neighbor.post.response.*;
import com.example.marketbackend.entity.NeighborPost;
import com.example.marketbackend.entity.NeighborPostLike;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.NeighborPostLikeRepository;
import com.example.marketbackend.repository.NeighborPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final NeighborPostLikeRepository neighborPostLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Response write(NeighborPostWriteRequest request) {
        long userId = authenticationService.getUserId();

        Optional<User> user = userRepository.findById(userId);

        NeighborPost post = NeighborPost.writeNeighborPost(request, user.get());

        neighborPostRepository.save(post);

        return new Response(ResponseMessage.NEIGHBOR_POST_WRITE, new NeighborPostWriteResponse(post.getId()));
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

    @Transactional
    public Response getPostDetail(long postId) {
        neighborPostRepository.increaseHits(postId);

        long userId = authenticationService.getUserId();

        Optional<NeighborPost> post = neighborPostRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);
        Optional<NeighborPostLike> like = neighborPostLikeRepository.findByPostIdAndUserId(postId, userId);

        NeighborPostDetail postDetail = NeighborPostDetail.makeNeighborPostDetail(post.get());

        boolean isMine = user.get().getId() == post.get().getUser().getId();
        boolean isLike = like.isPresent();

        NeighborPostUser userDetail = NeighborPostUser.makeNeighborPostUser(user.get(), isMine, isLike);

        return new Response(ResponseMessage.NEIGHBOR_POST_GET, new NeighborPostDetailResponse(postDetail, userDetail));
    }

    public Response getMyPosts(Pageable pageable) {
        long userId = authenticationService.getUserId();

        Page<NeighborPost> posts = neighborPostRepository.findByUserId(userId, pageable);
        long count = posts.getTotalElements();

        NeighborPostsListResponse response = new NeighborPostsListResponse(count, posts.stream().map(NeighborPostCardResponse::make).collect(Collectors.toList()));

        return new Response(ResponseMessage.MY_NEIGHBOR_POST_GET, response);
    }
}
