package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.favorite.response.FavoriteDeleteResponse;
import com.example.marketbackend.dto.favorite.response.FavoriteAddResponse;
import com.example.marketbackend.dto.favorite.response.FavoritePostResponse;
import com.example.marketbackend.dto.favorite.response.FavoritePostsResponse;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.ProductPostFavorite;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ProductPostFavoriteRepository;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserRepository userRepository;
    private final ProductPostRepository productPostRepository;
    private final ProductPostFavoriteRepository productPostFavoriteRepository;
    private final AuthenticationService authenticationService;

    public Response likePost(long postId) {
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

        return new Response(ResponseMessage.ADD_FAVORITE, new FavoriteAddResponse(favoriteCount));
    }

    public Response dislikePost(long postId) {
        long userId = authenticationService.getUserId();

        Optional<ProductPost> post = productPostRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);

        Optional<ProductPostFavorite> isFavorite = productPostFavoriteRepository.findByPostIdAndUserId(post.get().getId(), user.get().getId());

        if(isFavorite.isPresent()) {
            isFavorite.get().deleteLike();
            productPostFavoriteRepository.delete(isFavorite.get());
        }

        int favoriteCount = post.get().getFavorites();

        return new Response(ResponseMessage.DELETE_FAVORITE, new FavoriteDeleteResponse(favoriteCount));
    }

    public Response getFavoritePosts(Pageable pageable) {
        long userId = authenticationService.getUserId();

        Page<ProductPostFavorite> posts = productPostFavoriteRepository.findByUserId(userId, pageable);

        long count = posts.getTotalElements();

        List<FavoritePostResponse> list = posts.stream().map(FavoritePostResponse::createFavoritePostResponse).collect(Collectors.toList());

        return new Response(ResponseMessage.FAVORITE_POSTS_GET, new FavoritePostsResponse(count, list));
    }
}
