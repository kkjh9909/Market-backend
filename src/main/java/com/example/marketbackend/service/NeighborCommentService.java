package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.neighbor.comment.request.NeighborCommentWriteRequest;
import com.example.marketbackend.dto.neighbor.comment.response.NeighborCommentWriteResponse;
import com.example.marketbackend.entity.NeighborComment;
import com.example.marketbackend.entity.NeighborPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.NeighborCommentRepository;
import com.example.marketbackend.repository.NeighborPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NeighborCommentService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final NeighborPostRepository neighborPostRepository;
    private final NeighborCommentRepository neighborCommentRepository;

    @Transactional
    public Response write(long postId, NeighborCommentWriteRequest request) {
        long userId = authenticationService.getUserId();

        Optional<User> user = userRepository.findById(userId);
        Optional<NeighborPost> post = neighborPostRepository.findById(postId);

        Long parentId = request.getParentId() == null ? 0 : request.getParentId();

        Optional<NeighborComment> parent = neighborCommentRepository.findById(parentId);
        NeighborComment comment = NeighborComment.makeComment(request, user.get(), post.get(), parent.orElse(null));

        neighborCommentRepository.save(comment);

        return new Response(ResponseMessage.NEIGHBOR_COMMENT_ADD, new NeighborCommentWriteResponse(comment.getId()));
    }
}
