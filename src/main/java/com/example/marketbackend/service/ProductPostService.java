package com.example.marketbackend.service;

import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.post.response.ProductPostGetResponse;
import com.example.marketbackend.dto.post.response.ProductPostWriteResponse;
import com.example.marketbackend.dto.post.response.ProductPostsGetResponse;
import com.example.marketbackend.dto.post.vo.ProductPostDTO;
import com.example.marketbackend.dto.post.vo.ProductPostListDTO;
import com.example.marketbackend.dto.post.vo.UserInfoDTO;
import com.example.marketbackend.entity.ProductPhoto;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ProductPhotoRepository;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ProductPostWriteResponse write(ProductPostWriteRequest productPostWriteRequest) {
        long userId = getUserId();

        Optional<User> user = userRepository.findById(userId);

        ProductPost productPost = writeProductPost(productPostWriteRequest, user.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다.")));

        List<ProductPhoto> photos = new ArrayList<>();
        for (String url : productPostWriteRequest.getImages()) {
            ProductPhoto photo = ProductPhoto.from(productPost, url);
            photos.add(photo);
        }

        productPostRepository.save(productPost);
        productPhotoRepository.saveAll(photos);

        return new ProductPostWriteResponse(ResponseMessage.POST_WRITE);
    }

    public ProductPostGetResponse getPost(long postId) {
        Optional<ProductPost> post = productPostRepository.findByIdAndIsDeletedFalse(postId);

        long userId = getUserId();

        ProductPostDTO productPostDTO = ProductPostDTO.from(post.get(), userId);
        Optional<User> user = userRepository.findById(post.get().getUser().getId());

        UserInfoDTO userInfoDTO = UserInfoDTO.from(user.get());
        return new ProductPostGetResponse(ResponseMessage.POST_GET, productPostDTO, userInfoDTO);
    }

    public ProductPostsGetResponse getPosts(String address, Pageable pageable) {
        Page<ProductPost> posts;

        if(address.equals(""))
            posts = productPostRepository.findAll(pageable);
        else
            posts = productPostRepository.findByAddress(address, pageable);

        long count = posts.getTotalElements();

        return new ProductPostsGetResponse(ResponseMessage.POSTS_GET, count, posts.stream().map(ProductPostListDTO::from).collect(Collectors.toList()));
    }

    private long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return Long.parseLong(userDetails.getUsername());
            }
        }

        return 0L;
    }

}
