package com._3dhs.tnproject.comments.service;

import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.comments.dto.CommentsDTO;
import com._3dhs.tnproject.common.exceptionhandler.comments.CommentsWriteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsMapper commentsMapper;

    public List<CommentsDTO> selectCommentsList(CommentsDTO commentsDTO) {

        return commentsMapper.selectCommentsList(commentsDTO);
    }

    @Transactional
    public void writeComments(String comments, int memberCode) throws CommentsWriteException {
        int result = commentsMapper.writeComments(comments, memberCode);

        if (!(result > 0)) throw new CommentsWriteException("댓글 작성에 실패하였습니다.");

    }

    public void updateComments(int cmtCode, String cmtText) throws CommentsWriteException {
        int result = commentsMapper.updateComments(cmtCode, cmtText);

        if (!(result > 0)) throw new CommentsWriteException("댓글 수정에 실패하였습니다.");
    }

    public CommentsDTO getCommentByCommentsCode(int cmtCode) {
        return commentsMapper.getCommentByCommentsCode(cmtCode);
    }
}
