package com._3dhs.tnproject.like.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LikeController {

    private final PostService postService;

    @PostMapping("/like")
    @ResponseBody
    public ResponseEntity<String> likePost(@RequestBody LikeListDTO likeListDTO, @AuthenticationPrincipal MemberDTO memberDTO, Model model) {
        int memberCode = memberDTO.getMemberCode();
        try {
            boolean isLiked = postService.toggleLike(likeListDTO.getPostCode(), memberCode);
            String result = isLiked ? "true" : "false";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }
}
