package com._3dhs.tnproject.post.service;

import com._3dhs.tnproject.post.dao.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    public boolean getHasLiked(int postCode, int memberCode) {
        return likeMapper.getHasLiked(postCode, memberCode);
    }
}
