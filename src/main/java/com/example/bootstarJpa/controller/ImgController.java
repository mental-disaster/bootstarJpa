package com.example.bootstarJpa.controller;

import com.example.bootstarJpa.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImgController {

    final ImgService imgService;

    @ResponseBody
    @GetMapping("/{imgId}")
    public Resource showImage(@PathVariable Long imgId) {
        UrlResource img = imgService.id2Img(imgId);
        return img;
    }
}
