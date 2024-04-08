package com._3dhs.tnproject.comments.controller;

import com._3dhs.tnproject.comments.service.CommentsService;
import com._3dhs.tnproject.common.exceptionhandler.comments.CommentsWriteException;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/write")
    public String writeComments (@RequestParam String comments,
                                 @AuthenticationPrincipal MemberDTO loginMember) throws CommentsWriteException {
        int memberCode = loginMember.getMemberCode();
        commentsService.writeComments(comments, memberCode);

        return "/post/detail";
    }
}


