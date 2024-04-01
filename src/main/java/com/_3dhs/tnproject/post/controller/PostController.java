package com._3dhs.tnproject.post.controller;

import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
    public void blogDetailPage(Integer postCode) {
        //1. 해당하는 코드의 post정보를 불러오기
        //2. post가 유료글인지 판단하기
        //3. 유료글이라면 유료글 처리를 하는 곳으로 리다이렉트하기
    }
    @GetMapping("/likelist")
    public void blogLikeListPage(int memberCode, Model model) {
        List<PostDTO> likeList = postService.findLikeListPostByMemberCode(memberCode);
        model.addAttribute("likeList", likeList);
    }
}
