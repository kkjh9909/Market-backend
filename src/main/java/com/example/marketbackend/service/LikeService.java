package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.like.response.LikeAddResponse;
import com.example.marketbackend.dto.like.response.LikeDeleteResponse;
import com.example.marketbackend.entity.NeighborPost;
import com.example.marketbackend.entity.NeighborPostLike;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.NeighborPostLikeRepository;
import com.example.marketbackend.repository.NeighborPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final NeighborPostRepository neighborPostRepository;
    private final NeighborPostLikeRepository neighborPostLikeRepository;

    public Response likePost(long postId) {
        long userId = authenticationService.getUserId();

        Optional<NeighborPost> post = neighborPostRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);

        Optional<NeighborPostLike> isLike = neighborPostLikeRepository.findByPostIdAndUserId(post.get().getId(), user.get().getId());

        if(isLike.isEmpty()) {
            NeighborPostLike favorite = new NeighborPostLike(user.get(), post.get());
            favorite.addLike();
            neighborPostLikeRepository.save(favorite);
        }

        int likeCount = post.get().getLikes();

        return new Response(ResponseMessage.NEIGHBOR_ADD_LIKE, new LikeAddResponse(likeCount));
    }

    public Response dislikePost(long postId) {
        long userId = authenticationService.getUserId();

        Optional<NeighborPost> post = neighborPostRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);

        Optional<NeighborPostLike> isLike = neighborPostLikeRepository.findByPostIdAndUserId(post.get().getId(), user.get().getId());

        if(isLike.isPresent()) {
            isLike.get().deleteLike();
            neighborPostLikeRepository.delete(isLike.get());
        }

        int likeCount = post.get().getLikes();

        return new Response(ResponseMessage.NEIGHBOR_DELETE_LIKE, new LikeDeleteResponse(likeCount));
    }
}
