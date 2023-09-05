package com.example.marketbackend.dto.post.vo;

import com.example.marketbackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfoDTO {

    private String nickname;
    private String address;

    public static UserInfoDTO from(User user) {
        return UserInfoDTO.builder()
                .nickname(user.getNickname())
                .address(user.getAddress())
                .build();
    }
}
