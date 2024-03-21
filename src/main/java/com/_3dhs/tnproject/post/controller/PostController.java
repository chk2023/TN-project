package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) { this.postService = postService; }


}
