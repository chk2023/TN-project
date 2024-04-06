package com._3dhs.tnproject.comments.service;

import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.comments.dto.CommentsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsMapper commentsMapper;

    public List<CommentsDTO> selectCommentsList(CommentsDTO commentsDTO) {

        return commentsMapper.selectCommentsList(commentsDTO);
    }
}
