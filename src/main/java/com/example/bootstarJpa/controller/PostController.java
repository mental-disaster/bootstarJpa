package com.example.bootstarJpa.controller;

import com.example.bootstarJpa.model.vo.PostVo;
import com.example.bootstarJpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    final PostService postService;

    @PostMapping("")
    public String createPost(PostVo postVo, @RequestPart MultipartFile imgData, RedirectAttributes redirectAttributes, HttpServletRequest request){
        //이미지 확인
        String sourceFileName = imgData.getOriginalFilename();
        if(sourceFileName.equals("")){
            redirectAttributes.addFlashAttribute("imageError", "이미지를 첨부해주세요");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        postService.createPost(postVo,imgData);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId")Long id, HttpServletRequest request){
        postService.deletePost(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PutMapping("/{post_id}")
    public String updatePost(PostVo postVo, @RequestPart MultipartFile imgData, HttpServletRequest request){
        postService.updatePost(postVo, imgData);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
