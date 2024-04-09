package com._3dhs.tnproject.comments.controller;

import com._3dhs.tnproject.comments.service.CommentsService;
import com._3dhs.tnproject.common.exceptionhandler.comments.CommentsWriteException;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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


    @PostMapping("/update")
    public ResponseEntity<String> updateComments (@RequestBody Map<String, String> request) throws CommentsWriteException {
        int cmtCode = Integer.parseInt(request.get("cmtCode"));
        String cmtText = request.get("cmtText");

        commentsService.updateComments(cmtCode, cmtText);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }
}


