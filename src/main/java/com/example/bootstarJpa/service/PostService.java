package com.example.bootstarJpa.service;

import com.example.bootstarJpa.model.Post;
import com.example.bootstarJpa.model.User;
import com.example.bootstarJpa.model.vo.PostVo;
import com.example.bootstarJpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    final PostRepository postRepository;

    final ImgService imgService;

    //게시물 생성
    @Transactional
    public Post createPost(PostVo postVo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        postVo.setUser(user);
        return postRepository.save(new Post(postVo));
    }

    //게시물 삭제
    @Transactional
    public void deletePost(Long postId){
        Optional<Post> postSrc = postRepository.findById(postId);
        Post post = postSrc.get();
        File file = new File(post.getImg().getPath()+post.getImg().getName());
        file.delete();
        postRepository.deleteById(postId);
    }

    //게시물 수정
//    @Transactional
//    public void updatePost(PostVo postVo, MultipartFile imgData){
//        Post post = postRepository.save(new Post(postVo));
//        if(imgData != null){
//            File file = new File(postVo.getImgVo().getPath()+"/"+postVo.getImgVo().getName());
//            file.delete();
//            postVo.setImgVo(imgService.saveImg(imgData, post));
//        }
//    }

    //게시물 읽기
    public List<Post> selectAllPost(){
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public List<Post> selectAllPostByUserId(Long authorId){ return postRepository.findAllByUserIdOrderByCreatedAtDesc(authorId); }

    public Post selectPostById(Long postId){ return postRepository.findById(postId).get(); }
}
