package com.example.bootstar_jpa.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Timestamp createdAt;
    private String auth;
}
