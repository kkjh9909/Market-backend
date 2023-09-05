package com.example.marketbackend.service;

import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.dto.user.response.UserSignUpResponse;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.exception.LoginException;
import com.example.marketbackend.repository.UserRepository;
import com.example.marketbackend.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.example.marketbackend.entity.User.createUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest) {
        String password = passwordEncoder.encode(userSignUpRequest.getPassword());

        User user = createUser(userSignUpRequest, password);

        userRepository.save(user);

        return new UserSignUpResponse(ResponseMessage.SIGN_UP);
    }


    public UserSignInResponse signIn(UserSignInRequest userSignInRequest) {
        Optional<User> user = userRepository.findByUserId(userSignInRequest.getUserId());

        if(user.isEmpty())
            throw new LoginException("등록되지 않은 아이디입니다.");

        String inputPassword = userSignInRequest.getPassword();
        String encodedPassword = user.get().getPassword();

        if(passwordEncoder.matches(inputPassword, encodedPassword)) {
            String accessToken = jwtProvider.createAccessToken(user.get());
            return new UserSignInResponse(ResponseMessage.SIGN_IN, accessToken);
        }
        else
            throw new LoginException("비밀번호가 틀렸습니다.");
    }
}