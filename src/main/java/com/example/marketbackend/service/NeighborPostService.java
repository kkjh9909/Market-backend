package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.neighbor.post.request.NeighborPostWriteRequest;
import com.example.marketbackend.entity.NeighborPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.NeighborPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        return new Response(ResponseMessage.NEIGHBOR_POSTS_SEARCH, null);
    }
}
