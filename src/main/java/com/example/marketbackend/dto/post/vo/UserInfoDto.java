package com.example.marketbackend.dto.post.vo;

import com.example.marketbackend.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfoDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("address")
    private String address;

    @JsonProperty("profile")
    private String profile;

    @JsonProperty("my_post")
    private boolean isMine;

    @JsonProperty("is_like")
    private boolean isLike;

    public static UserInfoDto makeUserInfo(User user, boolean isMine, boolean isLike) {
        return UserInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .profile(user.getProfileImage())
                .isMine(isMine)
                .isLike(isLike)
                .build();
    }
}
