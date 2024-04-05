package com._3dhs.tnproject.post.service;

import com._3dhs.tnproject.post.dao.LikeMapper;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    public boolean getHasLiked(int postCode, int memberCode) {
        return likeMapper.getHasLiked(postCode, memberCode);
    }

    public List<LikeListDTO> getAllList() {
        return likeMapper.getAllList();
    }
}
