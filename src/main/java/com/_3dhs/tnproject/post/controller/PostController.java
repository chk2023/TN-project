package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
//TODO 폴더아이콘변경기능 넣기
    @GetMapping("/main")
    public void blogMainPage() {}
    @GetMapping("/folder_edit")
    public void folderEditPage() {}
    @GetMapping("/write")
    public void blogWritePage() {}
    @GetMapping("/temporary_storage/list")
    public void temporaryStorageListPage() {}
    @GetMapping("/list")
    public void blogListPage() {}
    @GetMapping("/detail")
    public void blogDetailPage() {}
    @GetMapping("likelist")
    public void findLikeListByMemberCode(int memberCode) {

    }
}
