package com.example.marketbackend.dto.post.vo;

import com.example.marketbackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfoDTO {

    private long id;
    private String nickname;
    private String address;
    private String profile;

    public static UserInfoDTO from(User user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .profile(user.getProfileImage())
                .build();
    }
}
