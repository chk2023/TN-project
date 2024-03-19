package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.post.dao.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
}
