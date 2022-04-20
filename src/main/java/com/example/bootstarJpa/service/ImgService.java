package com.example.bootstarJpa.service;

import com.example.bootstarJpa.model.Img;
import com.example.bootstarJpa.model.Post;
import com.example.bootstarJpa.model.User;
import com.example.bootstarJpa.model.vo.ImgVo;
import com.example.bootstarJpa.repository.ImgRepository;
import com.example.bootstarJpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImgService {

    @Value("${fileUrl.imgUrl}")
    private String imgUrl;

    final ImgRepository imgRepository;

    public UrlResource id2Img(Long imgId) {
        Optional<Img> imgSrc = imgRepository.findById(imgId);
        Img img = imgSrc.orElseGet(Img::new);
        UrlResource imgFile = null;
        try {
            imgFile = new UrlResource("file:"+img.getPath()+"/"+img.getName());
        } catch (MalformedURLException e) {
            //TODO: 잘못된 이미지 ID 처리 로직 필요
            e.printStackTrace();
        }
        return imgFile;
    }

    @Transactional
    public void saveImg(MultipartFile imgData, Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        //이미지 처리
        ImgVo imgVo = new ImgVo();
        String currTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
        String srcFileName = imgData.getOriginalFilename();
        String srcFileNameExt = FilenameUtils.getExtension(srcFileName).toLowerCase();
        String destFileName = RandomStringUtils.randomAlphanumeric(32) + currTime + "." + srcFileNameExt;
        String fullUrl = imgUrl + user.getId() + '/';
        File destFile = new File(fullUrl + destFileName);
        destFile.getParentFile().mkdir();
        try {
            imgData.transferTo(destFile);
        } catch (IOException e) {
            //TODO: 예외처리 로직 추가 필요
            e.printStackTrace();
        }

        imgVo.setPost(post);
        imgVo.setName(destFileName);
        imgVo.setPath(fullUrl);
        imgVo.setOriginalName(srcFileName);
        imgVo.setOriginalSize(destFile.length());

        imgRepository.save(new Img(imgVo));
    }

    @Transactional
    public void deleteImg(Post post){
        imgRepository.deleteById(post.getImg().getId());
        post.removeImg();
        imgRepository.flush();
    }
}
