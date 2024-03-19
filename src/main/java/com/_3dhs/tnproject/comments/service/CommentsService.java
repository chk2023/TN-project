package com._3dhs.tnproject.comments.service;

import com._3dhs.tnproject.comments.dao.CommentsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsMapper commentsMapper;
}
