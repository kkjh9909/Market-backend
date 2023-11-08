package com.example.marketbackend.service;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.user.request.UserIdCheckRequest;
import com.example.marketbackend.dto.user.request.UserProfileEditRequest;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserProfileResponse;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.dto.user.response.UserSignUpResponse;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.exception.LoginException;
import com.example.marketbackend.exception.response.DuplicateIdException;
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
    private final AuthenticationService authenticationService;

    @Transactional
    public Response signUp(UserSignUpRequest userSignUpRequest) {
        String password = passwordEncoder.encode(userSignUpRequest.getPassword());

        User user = createUser(userSignUpRequest, password);

        userRepository.save(user);

        return new Response(ResponseMessage.SIGN_UP, new UserSignUpResponse(user.getId()));
    }


    public Response signIn(UserSignInRequest userSignInRequest) {
        Optional<User> user = userRepository.findByUserId(userSignInRequest.getUserId());

        if(user.isEmpty())
            throw new LoginException("등록되지 않은 아이디입니다.");

        String inputPassword = userSignInRequest.getPassword();
        String encodedPassword = user.get().getPassword();

        if(passwordEncoder.matches(inputPassword, encodedPassword)) {
            String accessToken = jwtProvider.createAccessToken(user.get());
            return new Response(ResponseMessage.SIGN_IN, new UserSignInResponse(accessToken));
        }
        else
            throw new LoginException("비밀번호가 틀렸습니다.");
    }

    public Response getProfile() {
        long userId = authenticationService.getUserId();

        Optional<User> user = userRepository.findById(userId);

        return new Response(ResponseMessage.PROFILE_GET, new UserProfileResponse(user.get().getProfileImage(), user.get().getNickname(), user.get().getAddress()));
    }

    public Response getId() {
        long userId = authenticationService.getUserId();

        Optional<User> user = userRepository.findById(userId);

        return new Response(ResponseMessage.ID_GET, user.get().getId());
    }

    public Response checkId(UserIdCheckRequest request) {

        Optional<User> user = userRepository.findByUserId(request.getUserId());

        if(user.isPresent())
            throw new DuplicateIdException("ID", "ID 중복 오류");

        return new Response(ResponseMessage.CHECK_ID_SUCCESS, null);

    }

    @Transactional
    public Response editProfile(UserProfileEditRequest request) {
        long userId = authenticationService.getUserId();
        Optional<User> user = userRepository.findById(userId);

        user.get().editProfile(request);

        return new Response(ResponseMessage.EDIT_PROFILE, new UserProfileResponse(user.get().getProfileImage(), user.get().getNickname(), user.get().getAddress()));
    }
}
