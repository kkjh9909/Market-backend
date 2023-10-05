package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.neighbor.comment.request.NeighborCommentWriteRequest;
import com.example.marketbackend.dto.neighbor.comment.response.NeighborCommentListResponse;
import com.example.marketbackend.dto.neighbor.comment.response.NeighborCommentResponse;
import com.example.marketbackend.dto.neighbor.comment.response.NeighborCommentWriteResponse;
import com.example.marketbackend.dto.neighbor.comment.response.NeighborReplyResponse;
import com.example.marketbackend.entity.NeighborComment;
import com.example.marketbackend.entity.NeighborPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.NeighborCommentRepository;
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

        if(parent.isPresent()) {
            parent.get().getReplies().put(comment.getId(), comment);

            NeighborReplyResponse commentDto = NeighborReplyResponse.makeReplyResponse(comment, userId);

            return new Response(ResponseMessage.NEIGHBOR_COMMENT_ADD, new NeighborCommentWriteResponse(commentDto));
        }
        else {
            NeighborCommentResponse commentDto = NeighborCommentResponse.makeCommentResponse(comment, userId);

            return new Response(ResponseMessage.NEIGHBOR_COMMENT_ADD, new NeighborCommentWriteResponse(commentDto));
        }


    }

    public Response getCommentList(long postId, Pageable pageable) {
        long userId = authenticationService.getUserId();

        Page<NeighborComment> comments = neighborCommentRepository.findComments(postId, pageable);

        List<NeighborCommentResponse> commentDto = comments.stream()
                .map(comment -> NeighborCommentResponse.makeCommentResponse(comment, userId)).collect(Collectors.toList());

        long count = comments.getTotalElements();

        return new Response(ResponseMessage.NEIGHBOR_COMMENT_GET, new NeighborCommentListResponse(count, commentDto));
    }
}
