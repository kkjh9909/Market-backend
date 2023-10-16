package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.marketbackend.entity.User.createUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private User createTestUser() {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("test1", "password", "username", "nickname", "image", "address");

        String password = passwordEncoder.encode("password");

        return createUser(userSignUpRequest, password);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void signUp() {
        User user = createTestUser();

        userRepository.save(user);

        Optional<User> testUser = userRepository.findById(1L);

        assertEquals(user.getUserId(), testUser.get().getUserId());
    }

    @Test
    @DisplayName("로그인 테스트")
    public void signIn() {
        UserSignInRequest userSignInRequest = new UserSignInRequest("test1", "password");

        User user = createTestUser();

        userRepository.save(user);

        Response response = userService.signIn(userSignInRequest);

        assertEquals("로그인 성공", response.getMessage());
    }
}