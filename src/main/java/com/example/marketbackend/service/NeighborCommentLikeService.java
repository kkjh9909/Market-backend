package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.neighbor.comment.response.NeighborCommentLikeAddResponse;
import com.example.marketbackend.dto.neighbor.comment.response.NeighborCommentLikeDeleteResponse;
import com.example.marketbackend.entity.NeighborComment;
import com.example.marketbackend.entity.NeighborCommentLike;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.NeighborCommentLikeRepository;
import com.example.marketbackend.repository.NeighborCommentRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NeighborCommentLikeService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final NeighborCommentRepository neighborCommentRepository;
    private final NeighborCommentLikeRepository neighborCommentLikeRepository;

    public Response likeComment(long commentId) {
        long userId = authenticationService.getUserId();

        Optional<NeighborComment> comment = neighborCommentRepository.findById(commentId);
        Optional<User> user = userRepository.findById(userId);

        Optional<NeighborCommentLike> isLike = neighborCommentLikeRepository.findByCommentIdAndUserId(commentId, userId);

        if(isLike.isEmpty()) {
            NeighborCommentLike like = NeighborCommentLike.makeNeighborCommentLike(user.get(), comment.get());
            like.addLike();
            neighborCommentLikeRepository.save(like);
        }

        int likeCount = comment.get().getLikes();

        return new Response(ResponseMessage.NEIGHBOR_COMMENT_ADD_LIKE, new NeighborCommentLikeAddResponse(likeCount));
    }

    public Response dislikeComment(long commentId) {
        long userId = authenticationService.getUserId();

        Optional<NeighborComment> comment = neighborCommentRepository.findById(commentId);

        Optional<NeighborCommentLike> isLike = neighborCommentLikeRepository.findByCommentIdAndUserId(commentId, userId);

        if(isLike.isPresent()) {
            isLike.get().deleteLike();
            neighborCommentLikeRepository.delete(isLike.get());
        }

        int likeCount = comment.get().getLikes();

        return new Response(ResponseMessage.NEIGHBOR_COMMENT_DELETE_LIKE, new NeighborCommentLikeDeleteResponse(likeCount));
    }
}
