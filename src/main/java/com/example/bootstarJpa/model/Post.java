package com.example.bootstarJpa.model;

import com.example.bootstarJpa.model.vo.PostVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authorId;
    private String caption;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id")
    private Img img;

    public Post(PostVo vo){
        this.authorId = vo.getAuthorId();
        this.caption = vo.getCaption();
    }
}
