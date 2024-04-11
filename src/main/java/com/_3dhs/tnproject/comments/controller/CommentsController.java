package com._3dhs.tnproject.comments.controller;

import com._3dhs.tnproject.comments.dto.CommentsDTO;
import com._3dhs.tnproject.comments.service.CommentsService;
import com._3dhs.tnproject.common.exceptionhandler.comments.CommentsWriteException;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/write")
    public ResponseEntity<String> writeComments (@RequestBody Map<String, String> request,
                                 @AuthenticationPrincipal MemberDTO loginMember) throws CommentsWriteException {
        int memberCode = loginMember.getMemberCode();
        String cmt = request.get("cmt");
        commentsService.writeComments(cmt, memberCode);

        return ResponseEntity.ok("댓글이 성공적으로 작성되었습니다.");
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateComments (@RequestBody Map<String, String> request) throws CommentsWriteException {
        int cmtCode = Integer.parseInt(request.get("cmtCode"));
        String cmtText = request.get("cmtText");

        commentsService.updateComments(cmtCode, cmtText);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteComments (@RequestBody Map<String, String> request) throws CommentsWriteException {
        int cmtCode = Integer.parseInt(request.get("cmtCode"));
        commentsService.deleteComments(cmtCode);
        return ResponseEntity.ok("댓글 성공적으로 삭제되었습니다");
    }

    @GetMapping("/load")
    public List<CommentsDTO> getComments(CommentsDTO commentsDTO) {
        return commentsService.selectCommentsList(commentsDTO);
    }
}


