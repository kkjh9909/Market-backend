package com.example.marketbackend.service;

import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.example.marketbackend.entity.User.createUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    public void signUp() {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("test1", "password", "username", "nickname", "image", "address");

        User user = createUser(userSignUpRequest);

        userRepository.save(user);

        Optional<User> testUser = userRepository.findById(1L);

        assertEquals(user.getUserId(), testUser.get().getUserId());
    }
}