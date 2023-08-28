package com.example.marketbackend.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userId;

    private String password;

    private String userName;

    private String nickname;

    private String address;

    private String imgUrl;

    private LocalDateTime createdAt;
}
