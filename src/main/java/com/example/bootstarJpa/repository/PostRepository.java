package com.example.bootstarJpa.repository;

import com.example.bootstarJpa.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long id);

    @Query("select p from Post p order by p.createdAt desc ")
    List<Post> findLimitPost(Pageable pageable);

    @Query("select p from Post p where p.id=?1 order by p.createdAt desc")
    List<Post> findLimitPostByUserId(Long id, Pageable pageable);
}
