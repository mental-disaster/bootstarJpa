package com.example.bootstarJpa.model.vo;

import com.example.bootstarJpa.model.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserVo {
    private Long id;
    @Email(message = "올바르지 않은 이메일 형식입니다")
    private String email;
    @Size(min = 4,max = 16,message = "비밀번호는 4자 이상 16자 이하로 입력하십시오")
    private String password;
    private String createdAt;
    private String auth;
    private List<Post> posts;
}
