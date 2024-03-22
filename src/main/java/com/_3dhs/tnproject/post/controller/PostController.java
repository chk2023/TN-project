package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("post/likelist")
    public void findLikeListByMemberCode(int memberCode) {

    }
}
