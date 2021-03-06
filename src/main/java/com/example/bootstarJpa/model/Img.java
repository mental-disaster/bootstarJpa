package com.example.bootstarJpa.model;

import com.example.bootstarJpa.model.vo.ImgVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private String path;
    private String originalName;
    private Long originalSize;
    @OneToOne
    @JoinColumn
    @JsonIgnore
    private Post post;

    public Img(ImgVo vo){
        this.id = vo.getId();
        this.name = vo.getName();
        this.path = vo.getPath();
        this.originalName = vo.getOriginalName();
        this.originalSize = vo.getOriginalSize();
        this.post = vo.getPost();
    }
}
