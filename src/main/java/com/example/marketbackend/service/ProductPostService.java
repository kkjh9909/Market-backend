package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.post.response.*;
import com.example.marketbackend.dto.post.vo.ProductPostDto;
import com.example.marketbackend.dto.post.vo.ProductPostListDTO;
import com.example.marketbackend.dto.post.vo.UserInfoDto;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.ProductPostFavorite;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ProductPhotoRepository;
import com.example.marketbackend.repository.ProductPostFavoriteRepository;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.marketbackend.entity.ProductPost.writeProductPost;

@Service
@RequiredArgsConstructor
public class ProductPostService {

    private final ProductPostRepository productPostRepository;
    private final UserRepository userRepository;
    private final ProductPhotoRepository productPhotoRepository;
    private final ProductPostFavoriteRepository productPostFavoriteRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public ProductPostWriteResponse write(ProductPostWriteRequest productPostWriteRequest) {
        long userId = authenticationService.getUserId();

        Optional<User> user = userRepository.findById(userId);

        ProductPost productPost = writeProductPost(productPostWriteRequest, user.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다.")));

        productPostRepository.save(productPost);

        return new ProductPostWriteResponse(ResponseMessage.PRODUCT_POST_WRITE);
    }

    @Transactional
    public ProductPostGetResponse getPostDetail(long postId) {
        productPostRepository.increaseHits(postId);

        long userId = authenticationService.getUserId();

        Optional<ProductPost> post = productPostRepository.findByIdAndIsDeletedFalse(postId);
        Optional<ProductPostFavorite> like = productPostFavoriteRepository.findByPostIdAndUserId(post.get().getId(), userId);

        boolean isMine = post.get().getUser().getId() == userId;
        boolean isLike = like.isPresent();

        ProductPostDto productPostDTO = ProductPostDto.makeProductPostDto(post.get());

        UserInfoDto userInfoDTO = UserInfoDto.makeUserInfo(post.get().getUser(), isMine, isLike);

        return new ProductPostGetResponse(ResponseMessage.POST_GET, productPostDTO, userInfoDTO);
    }

    public Response getPosts(String address, Pageable pageable) {
        Page<ProductPost> posts;

        if(address.equals(""))
            posts = productPostRepository.findAll(pageable);
        else
            posts = productPostRepository.findByAddressOrderByCreatedAtDesc(address, pageable);

        long count = posts.getTotalElements();

        ProductPostsGetResponse productPostsGetResponse = new ProductPostsGetResponse(count, posts.stream().map(ProductPostListDTO::from).collect(Collectors.toList()));

        return new Response(ResponseMessage.POSTS_GET, productPostsGetResponse);
    }

    public ProductPostsByCategoryResponse getPostsByCategory(String category) {
        List<ProductPost> posts = productPostRepository.findAllByCategory(category);

        long count = posts.size();

        return new ProductPostsByCategoryResponse(ResponseMessage.POSTS_GET, count, posts.stream().map(ProductPostListDTO::from).collect(Collectors.toList()));
    }

    public Response getMyPosts(Pageable pageable) {
        long userId = authenticationService.getUserId();

        Page<ProductPost> posts = productPostRepository.findByUserId(userId, pageable);
        long count = posts.getTotalElements();

        ProductPostsGetResponse productPostsGetResponse = new ProductPostsGetResponse(count, posts.stream().map(ProductPostListDTO::from).collect(Collectors.toList()));

        return new Response(ResponseMessage.MY_POSTS_GET, productPostsGetResponse);
    }

    public Response getMyPost(long postId) {
        Optional<ProductPost> post = productPostRepository.findById(postId);

        MyProductPostResponse myProductPostResponse = MyProductPostResponse.makeMyPostResponse(post.get());

        return new Response(ResponseMessage.MY_POST_GET, myProductPostResponse);
    }

    @Transactional
    public Response updateMyPost(long postId, ProductPostWriteRequest request) {
        Optional<ProductPost> post = productPostRepository.findById(postId);

        productPhotoRepository.deleteByPostId(postId);
        post.get().update(request);

        return new Response(ResponseMessage.MY_POST_UPDATE, null);
    }

    public Response deleteMyPost(long postId) {
        Optional<ProductPost> post = productPostRepository.findById(postId);

        productPostRepository.delete(post.get());

        return new Response(ResponseMessage.MY_POST_DELETE, null);
    }

    public Response searchPost(String address, String keyword, Pageable pageable) {
        Page<ProductPost> posts = productPostRepository.findByKeyword(address, keyword, keyword, keyword, pageable);

        long count = posts.getTotalElements();


        List<ProductPostListDTO> collect = posts.stream().map(ProductPostListDTO::from).collect(Collectors.toList());

        ProductPostsSearchResponse result = new ProductPostsSearchResponse(count, collect);

        return new Response(ResponseMessage.POSTS_SEARCH, result);
    }
}
