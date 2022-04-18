package com.example.bootstarJpa.repository;

import com.example.bootstarJpa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    <Post extends Long> Post save(Post entity);


}
