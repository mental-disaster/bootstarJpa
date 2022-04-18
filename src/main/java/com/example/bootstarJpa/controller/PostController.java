package com.example.bootstarJpa.controller;

import com.example.bootstarJpa.model.Post;
import com.example.bootstarJpa.model.vo.PostVo;
import com.example.bootstarJpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class PostController {

    @Value("${fileUrl.imgUrl}")
    private String imgUrl;

    final PostService postService;

    @PostMapping("/post")
    public String createPost(PostVo postVo, @RequestPart MultipartFile imgData, RedirectAttributes redirectAttributes, HttpServletRequest request, Authentication authentication){
        //이미지 처리
        String sourceFileName = imgData.getOriginalFilename();
        if(sourceFileName.equals("")){
            redirectAttributes.addFlashAttribute("imageError", "이미지를 첨부해주세요");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        String imgName = postService.saveImage(imgData);
        post.setImage(imgName);

        User user = (User) authentication.getPrincipal();
        post.setAuthor_id(user.getUser_id());

        postService.createPost(post);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @ResponseBody
    @GetMapping("/post/image/{imgName}")
    public Resource showImage(@PathVariable String imgName) throws MalformedURLException{
        return new UrlResource("file:"+imgUrl+imgName);
    }

    @DeleteMapping("/post/{postId}")
    public String deletePost(@PathVariable("postId")int id, HttpServletRequest request){
        postService.deletePost(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PutMapping("/post/{post_id}")
    public String updatePost(Post post, @RequestPart MultipartFile imgData, HttpServletRequest request){
        String sourceFileName = imgData.getOriginalFilename();
        if(!sourceFileName.equals("")){
            String imgName = postService.saveImage(imgData);
            post.setImage(imgName);
        }

        postService.updatePost(post);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
