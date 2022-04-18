package com.example.bootstarJpa.model.vo;

import lombok.Data;

@Data
public class ImgVo {
    private Long id;
    private Long postId;
    private String name;
    private String path;
    private String originalName;
    private Long originalSize;
}
