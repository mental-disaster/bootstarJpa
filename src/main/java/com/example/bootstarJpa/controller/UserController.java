package com.example.bootstarJpa.controller;

import com.example.bootstarJpa.model.Post;
import com.example.bootstarJpa.model.User;
import com.example.bootstarJpa.model.vo.UserVo;
import com.example.bootstarJpa.service.PostService;
import com.example.bootstarJpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    final UserService userService;
    final PostService postService;

    //메인화면(로그인 화면)
    @GetMapping("")
    public String root(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(user != null){
            return "redirect:/hello";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() { return "/login"; }

    @GetMapping("/loginFail")
    public String accessDenied() { return "loginFail"; }

    //회원가입
    @GetMapping("/signup")
    public String signUpForm(){ return "/signup"; }

    @PostMapping("/signup")
    public String signup(@Valid UserVo vo, Errors errors, Model model){

        boolean trigger = false;
        //아이디 중복 검증 - DB에서 아이디 기반 검색에 성공하면 이미 존재하는 아이디
        User userInfo = userService.loadUserByEmail(vo.getEmail());
        if(userInfo != null){
            System.out.println(userInfo);
            model.addAttribute("emailError", "이미 존재하는 회원입니다");
            trigger = true;
        }
        //입력값 유효성 검증
        if(errors.hasErrors()){
            Map<String, String> validResult = userService.validHandling(errors);
            for(String key:validResult.keySet()){
                model.addAttribute(key, validResult.get(key));
            }
            trigger = true;
        }
        if(trigger){
            return "/signup";
        }

        //신규 유저 생성
        userService.joinUser(vo);
        return "redirect:/login";
    }

    //로그인 후 메인화면(모든 유저 타임라인)
    @GetMapping("/hello")
    public String userAccess(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        List<Post> posts = postService.selectAllPost();
        model.addAttribute("posts",posts);
        return "/hello";
    }

    //개인 타임라인
    @GetMapping("/personal/{userId}")
    public String personalPage(@PathVariable("userId")Long id, Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        List<Post> posts = postService.selectAllPostByUserId(id);
        model.addAttribute("posts",posts);
        return "/personal";
    }
}
