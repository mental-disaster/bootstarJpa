package com.example.bootstarJpa.service;

import com.example.bootstarJpa.model.User;
import com.example.bootstarJpa.model.vo.UserVo;
import com.example.bootstarJpa.repository.UserRepository;
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

    private final UserRepository userRepository;

    //회원가입 유저 저장
    @Transactional
    public void joinUser(UserVo vo){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        vo.setAuth("USER");
        User user = new User(vo);
        userRepository.save(user);
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
    public User loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User loadUserByUsername(String email){
        return userRepository.findByEmail(email);
    }
}
