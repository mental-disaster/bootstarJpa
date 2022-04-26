package com.example.bootstarJpa.controller;

import com.example.bootstarJpa.model.Post;
import com.example.bootstarJpa.model.vo.PostVo;
import com.example.bootstarJpa.model.vo.mapper.PostMapper;
import com.example.bootstarJpa.service.ImgService;
import com.example.bootstarJpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    final PostService postService;
    final ImgService imgService;
    final PostMapper postMapper;

    @ResponseBody
    @GetMapping("")
    public List<PostVo> selectLimitPost(@RequestParam int page, @RequestParam int limit){
        List<Post> posts = postService.selectLimitPost(page, limit);
        System.out.println(posts.get(0).getId());
        List<PostVo> postVos = posts.stream()
                .map(postMapper::mapEntityVo)
                .collect(Collectors.toList());
        System.out.println(postVos);
        return postVos;
    }

    @PostMapping("")
    public String createPost(PostVo postVo, @RequestPart MultipartFile imgData, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //이미지 확인
        String sourceFileName = imgData.getOriginalFilename();
        if(sourceFileName.equals("")){
            redirectAttributes.addFlashAttribute("imageError", "이미지를 첨부해주세요");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }

        Post post = postService.createPost(postVo);
        imgService.saveImg(imgData, post);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId")Long id, HttpServletRequest request){
        postService.deletePost(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PutMapping("")
    public String updatePost(PostVo postVo, @RequestPart MultipartFile imgData, HttpServletRequest request){
        String sourceFileName = imgData.getOriginalFilename();
        String referer = request.getHeader("Referer");
        if(sourceFileName.equals("") && postVo.getCaption().equals("")){
            return "redirect:" + referer;
        }

        Post post = postService.selectPostById(postVo.getId());
        if(!sourceFileName.equals("")){
            File file = new File(post.getImg().getPath()+post.getImg().getName());
            file.delete();
            imgService.deleteImg(post);
            imgService.saveImg(imgData, post);
        }
        
        postService.updatePost(postVo);

        return "redirect:" + referer;
    }
}
