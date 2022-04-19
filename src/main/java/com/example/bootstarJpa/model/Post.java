package com.example.bootstarJpa.model;

import com.example.bootstarJpa.model.vo.PostVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Img img;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    public Post(PostVo vo){
        this.caption = vo.getCaption();
    }
}
