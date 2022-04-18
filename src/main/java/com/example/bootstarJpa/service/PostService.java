package com.example.bootstarJpa.service;

import com.example.bootstar.domain.Post;
import com.example.bootstar.domain.User;
import com.example.bootstar.mapper.PostMapper;
import com.example.bootstarJpa.model.vo.ImgVo;
import com.example.bootstarJpa.model.vo.PostVo;
import com.example.bootstarJpa.model.vo.UserVo;
import com.example.bootstarJpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    @Value("${fileUrl.imgUrl}")
    private String imgUrl;

    final PostRepository postRepository;

    //게시물 생성
    @Transactional
    public void createPost(PostVo postVo, MultipartFile imgData){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserVo userVo = (UserVo) authentication.getPrincipal();

        //이미지 처리
        ImgVo imgVo = new ImgVo();
        String currTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
        String srcFileName = imgData.getOriginalFilename();
        String srcFileNameExt = FilenameUtils.getExtension(srcFileName).toLowerCase();
        String destFileName = RandomStringUtils.randomAlphanumeric(32)+currTime+"."+srcFileNameExt;
        File destFile = new File(imgUrl + userVo.getId() + '/' + destFileName);
        destFile.getParentFile().mkdir();
        try {
            imgData.transferTo(destFile);
        } catch (IOException e) {
            //TODO: 예외처리 로직 추가 필요
            e.printStackTrace();
        }
        imgVo.setName(destFileName);
        imgVo.setPath(String.valueOf(userVo.getId())+'/');
        imgVo.setOriginalName(srcFileName);
        imgVo.setOriginalSize(destFile.length());


        postVo.setAuthorId(userVo.getId());
        postVo.setImgVo(imgVo);

        //데이터 저장 명령(레포지토리)
    }

    //게시물 삭제
    @Transactional
    public void deletePost(int postId){
        List<Map<String ,Object>> post = postMapper.getPostByPostId(postId);
        String destFileUrl = imgUrl+post.get(0).get("image");
        File file = new File(destFileUrl);
        file.delete();

        //게시물 삭제 명령
    }

    //게시물 수정
    @Transactional
    public void updatePost(Post post){
        if(post.getImage() != null){
            List<Map<String ,Object>> oldPost = postMapper.getPostByPostId(post.getPost_id());
            String destFileUrl = imgUrl+oldPost.get(0).get("image");
            File file = new File(destFileUrl);
            file.delete();
        }
        postMapper.changePost(post);
    }

    //게시물 읽기
    public List<Map<String ,Object>> selectAllPost(){
        return postMapper.getAllPost();
    }

    public List<Map<String ,Object>> selectPostByUserId(int authorId){
        return postMapper.getPostByUserId(authorId);
    }

    //이미지 처리
    public ImgVo saveImage(MultipartFile imgData){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserVo userVo = (UserVo) authentication.getPrincipal();
        ImgVo imgVo = new ImgVo();
        //중복방지를 위한 시간
        String currTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
        //원본파일명
        String srcFileName = imgData.getOriginalFilename();
        //확장자
        String srcFileNameExt = FilenameUtils.getExtension(srcFileName).toLowerCase();
        //저장파일명
        String destFileName = RandomStringUtils.randomAlphanumeric(32)+currTime+"."+srcFileNameExt;
        File destFile = new File(imgUrl + userVo.getId() + '/' + destFileName);
        destFile.getParentFile().mkdir();
        try {
            imgData.transferTo(destFile);
        } catch (IOException e) {
            //TODO: 예외처리 로직 추가 필요
            e.printStackTrace();
        }

        imgVo.setName(destFileName);
        imgVo.setPath(String.valueOf(userVo.getId())+'/');
        imgVo.setOriginalName(srcFileName);
        imgVo.setOriginalSize(destFile.length());

        return imgVo;
    }
}
