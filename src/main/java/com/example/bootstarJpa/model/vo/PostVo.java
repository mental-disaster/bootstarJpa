package com.example.bootstarJpa.model.vo;

import com.example.bootstarJpa.model.Img;
import com.example.bootstarJpa.model.User;
import lombok.Data;

@Data
public class PostVo {
    private Long id;
    private String caption;
    private String createdAt;
    private String updatedAt;
    private Img img;
    private User user;
}
