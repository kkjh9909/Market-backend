package com.example.marketbackend.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class ProductPost {

    @Id @GeneratedValue
    private long id;

    private String title;

    private String body;

    private int price;

    private boolean isDeleted;

    private int chatroomCount;

    private int hits;

    private int favorites;

    private String category;
}
