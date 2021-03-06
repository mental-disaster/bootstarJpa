package com.example.bootstarJpa.model;

import com.example.bootstarJpa.model.vo.PostVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private String createdAt;
    private String updatedAt;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Img img;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    public Post(PostVo vo){
        this.id = vo.getId();
        this.caption = vo.getCaption();
        this.createdAt = vo.getCreatedAt();
        this.updatedAt = vo.getUpdatedAt();
        this.user = vo.getUser();
    }

    public void removeImg(){
        this.img = null;
    }
}
