package com.example.bootstarJpa.model.vo;

import com.example.bootstarJpa.model.Img;
import com.example.bootstarJpa.model.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostVo {
    private Long id;
    private String caption;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Img img;
    private User user;


}
