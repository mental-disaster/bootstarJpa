package com.example.bootstarJpa.model.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostVo {
    private Long id;
    private Long authorId;
    private String caption;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private ImgVo imgVo;
}
