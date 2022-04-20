package com.example.bootstarJpa.model;

import com.example.bootstarJpa.model.vo.UserVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private Timestamp createdAt;
    private String auth;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    public User(UserVo vo){
        this.id = vo.getId();
        this.email = vo.getEmail();
        this.password = vo.getPassword();
        this.createdAt = vo.getCreatedAt();
        this.auth = vo.getAuth();
    }


    //UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singletonList(new SimpleGrantedAuthority(this.auth));
    }

    @Override
    public String getUsername(){
        return this.email;
    }

    @Override
    public String getPassword(){ return this.password; }

    @Override
    public  boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
