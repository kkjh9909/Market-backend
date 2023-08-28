package com.example.marketbackend.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class ProductCategory {

    @Id @GeneratedValue
    private long id;

    private String category;
}
