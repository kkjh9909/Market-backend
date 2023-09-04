package com.example.marketbackend.service;

import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.dto.user.response.UserSignUpResponse;
import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.example.marketbackend.entity.User.createUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest) {
        User user = createUser(userSignUpRequest);

        userRepository.save(user);

        return new UserSignUpResponse(ResponseMessage.SIGN_UP);
    }


    public UserSignInResponse signIn(UserSignInRequest userSignInRequest) {
        Optional<User> user = userRepository.findByUserId(userSignInRequest.getUserId());

        if(user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        if(user.get().getPassword().equals(userSignInRequest.getPassword()))
            return new UserSignInResponse(ResponseMessage.SIGN_IN);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password Unmatched");
    }
}
