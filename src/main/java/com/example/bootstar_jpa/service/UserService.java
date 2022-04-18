package com.example.bootstar.service;

import com.example.bootstar.domain.User;
import com.example.bootstar.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    final UserMapper userMapper;

    //회원가입 유저 저장
    @Transactional
    public void joinUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuth("USER");
        userMapper.saveUser(user);
    }

    //회원가입 유효성 검증
    public Map<String, String> validHandling(Errors errors){
        Map<String, String> validResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()){
            String errorKeyName = String.format("%sError", error.getField());
            validResult.put(errorKeyName, error.getDefaultMessage());
        }
        return validResult;
    }

    //로그인 아이디 추출
    @Override
    public User loadUserByUsername(String username) {
        User user = userMapper.getUserAccount(username);
        return user;
    }
}
