package com.example.bootstarJpa.model.vo.mapper;

import com.example.bootstarJpa.model.Post;
import com.example.bootstarJpa.model.vo.PostVo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public final PostVo mapEntityVo(Post post){
        return modelMapper.map(post, PostVo.class);
    }
}
