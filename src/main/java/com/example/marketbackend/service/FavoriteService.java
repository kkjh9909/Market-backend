package com.example.marketbackend.service;

import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.post.response.FavoriteDeleteResponse;
import com.example.marketbackend.dto.post.response.FavoritePostResponse;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.ProductPostFavorite;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ProductPostFavoriteRepository;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserRepository userRepository;
    private final ProductPostRepository productPostRepository;
    private final ProductPostFavoriteRepository productPostFavoriteRepository;
    private final AuthenticationService authenticationService;

    public FavoritePostResponse likePost(long postId) {
        long userId = authenticationService.getUserId();

        Optional<ProductPost> post = productPostRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);

        Optional<ProductPostFavorite> isFavorite = productPostFavoriteRepository.findByPostIdAndUserId(post.get().getId(), user.get().getId());

        if(isFavorite.isEmpty()) {
            ProductPostFavorite favorite = new ProductPostFavorite(user.get(), post.get());
            favorite.addLike();
            productPostFavoriteRepository.save(favorite);
        }

        int favoriteCount = post.get().getFavorites();

        return new FavoritePostResponse(ResponseMessage.ADD_FAVORITE, favoriteCount);
    }

    public FavoriteDeleteResponse dislikePost(long postId) {
        long userId = authenticationService.getUserId();

        Optional<ProductPost> post = productPostRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);

        Optional<ProductPostFavorite> isFavorite = productPostFavoriteRepository.findByPostIdAndUserId(post.get().getId(), user.get().getId());

        if(isFavorite.isPresent()) {
            isFavorite.get().deleteLike();
            productPostFavoriteRepository.delete(isFavorite.get());
        }

        int favoriteCount = post.get().getFavorites();

        return new FavoriteDeleteResponse(ResponseMessage.DELETE_FAVORITE, favoriteCount);
    }
}
