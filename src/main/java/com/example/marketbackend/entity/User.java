package com.example.marketbackend.entity;

import com.example.marketbackend.dto.user.request.UserProfileEditRequest;
import com.example.marketbackend.dto.user.request.UserSignUpRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userId;

    private String password;

    private String userName;

    private String nickname;

    private String address;

    private String profileImage;

    private LocalDateTime createdAt;

    public User() {}
    public static User createUser(UserSignUpRequest userSignUpRequest, String password) {
        return User.builder().userId(userSignUpRequest.getUserId())
                .password(password)
                .userName(userSignUpRequest.getUsername())
                .nickname(userSignUpRequest.getNickname())
                .address(userSignUpRequest.getAddress())
                .profileImage(userSignUpRequest.getProfileImage())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public User editProfile(UserProfileEditRequest request) {
        this.nickname = request.getNickname();
        this.address = request.getAddress();
        this.profileImage = request.getImage();

        return this;
    }
}
