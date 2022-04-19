package com.example.bootstarJpa.service;

import com.example.bootstarJpa.model.Post;
import com.example.bootstarJpa.model.User;
import com.example.bootstarJpa.model.vo.PostVo;
import com.example.bootstarJpa.model.vo.UserVo;
import com.example.bootstarJpa.repository.ImgRepository;
import com.example.bootstarJpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void createPost(PostVo postVo, MultipartFile imgData){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());

        postVo.setUserVo(userVo);
        System.out.println(postVo);
        Post post = postRepository.save(new Post(postVo));

        imgService.saveImg(imgData, post);
    }

    //게시물 삭제
    @Transactional
    public void deletePost(Long postId){
        Optional<Post> postSrc = postRepository.findById(postId);
        Post post = postSrc.get();
        File file = new File(post.getImg().getPath()+"/"+post.getImg().getName());
        file.delete();
        postRepository.deleteById(postId);
    }

    //게시물 수정
    @Transactional
    public void updatePost(PostVo postVo, MultipartFile imgData){
        Post post = postRepository.save(new Post(postVo));
        if(imgData != null){
            File file = new File(postVo.getImgVo().getPath()+"/"+postVo.getImgVo().getName());
            file.delete();
            postVo.setImgVo(imgService.saveImg(imgData, post));
        }
    }

    //게시물 읽기
    public List<Post> selectAllPost(){
        return postRepository.findAll();
    }

    public List<Post> selectPostByUserId(Long authorId){ return postRepository.findAllByUser_Id(authorId); }
}
