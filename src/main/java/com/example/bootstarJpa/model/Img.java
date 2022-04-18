package com.example.bootstarJpa.model;

import com.example.bootstarJpa.model.vo.ImgVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postId;
    private String name;
    private String path;
    private String originalName;
    private Long originalSize;


    public Img(ImgVo vo){
        this.postId = vo.getPostId();
        this.name = vo.getName();
        this.path = vo.getPath();
        this.originalName = vo.getOriginalName();
        this.originalSize = vo.getOriginalSize();
    }
}
