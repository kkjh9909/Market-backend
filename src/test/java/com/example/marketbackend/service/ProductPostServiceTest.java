package com.example.marketbackend.service;

import com.example.marketbackend.dto.post.request.ProductPostWriteRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.ProductPostRepository;
import com.example.marketbackend.repository.UserRepository;
import com.example.marketbackend.security.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.example.marketbackend.entity.User.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductPostServiceTest {

    @Autowired
    private ProductPostService productPostService;

    @Autowired
    private ProductPostRepository productPostRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private User createTestUser() {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("test11", "password", "username", "nickname", "image", "address");

        String password = passwordEncoder.encode("password");
        User user = createUser(userSignUpRequest, password);

        userRepository.save(user);

        return user;
    }

    @Test
    @DisplayName("판매글 조회 기능 테스트")
    public void getPost() {

    }
}