package com.example.marketbackend.service;

import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.post.response.ProductPostGetResponse;
import com.example.marketbackend.dto.post.response.ProductPostWriteResponse;
import com.example.marketbackend.dto.post.vo.ProductPostDTO;
import com.example.marketbackend.dto.post.vo.UserInfoDTO;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.marketbackend.entity.ProductPost.writeProductPost;

@Service
@RequiredArgsConstructor
public class ProductPostService {

    private final ProductPostRepository productPostRepository;
    private final UserRepository userRepository;

    public ProductPostWriteResponse write(ProductPostWriteRequest productPostWriteRequest) {
        long userId = getUserId();

        Optional<User> user = userRepository.findById(userId);

        ProductPost productPost = writeProductPost(productPostWriteRequest, user.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다.")));

        productPostRepository.save(productPost);

        return new ProductPostWriteResponse(ResponseMessage.POST_WRITE);
    }

    public ProductPostGetResponse getPost(long postId) {
        Optional<ProductPost> post = productPostRepository.findByIdAndIsDeletedFalse(postId);

        long userId = getUserId();

        ProductPostDTO productPostDTO = ProductPostDTO.from(post.orElseThrow(() -> new RuntimeException("해당하는 글이 존재하지 않습니다.")), getUserId());

        Optional<User> user = userRepository.findById(userId);

        UserInfoDTO userInfoDTO = UserInfoDTO.from(user.orElseThrow(() -> new RuntimeException("해당하는 유저가 존재하지 않습니다.")));

        return new ProductPostGetResponse(ResponseMessage.POST_GET, productPostDTO, userInfoDTO);
    }

    private long getUserId() {
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.parseLong(principal.getUsername());
    }
}
