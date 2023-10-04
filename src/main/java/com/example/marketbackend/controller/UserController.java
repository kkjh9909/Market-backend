package com.example.marketbackend.controller;

import com.example.marketbackend.dto.Response;
import com.example.marketbackend.dto.ResponseMessage;
import com.example.marketbackend.dto.user.request.UserSignInRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import com.example.marketbackend.dto.user.response.UserSignInResponse;
import com.example.marketbackend.dto.user.response.UserSignUpResponse;
import com.example.marketbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
        Response response = userService.signUp(userSignUpRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserSignInRequest userSignInRequest) {
        Response response = userService.signIn(userSignInRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Response response = userService.getProfile();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getId() {
        Response response = userService.getId();

        return ResponseEntity.ok(response);
    }
}
