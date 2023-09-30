package com.example.marketbackend.dto.neighbor.post.response;

import com.example.marketbackend.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NeighborPostUser {

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

    public static NeighborPostUser makeNeighborPostUser(User user, boolean isMine) {
        return NeighborPostUser.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .profile(user.getProfileImage())
                .isMine(isMine)
                .build();
    }
}
