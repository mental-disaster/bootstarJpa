package com.example.bootstarJpa.model.vo;

import com.example.bootstarJpa.model.Post;
import lombok.Data;

@Data
public class ImgVo {
    private Long id;
    private String name;
    private String path;
    private String originalName;
    private Long originalSize;
    private Post post;
}
