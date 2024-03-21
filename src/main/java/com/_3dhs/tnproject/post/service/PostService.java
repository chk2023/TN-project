package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;

    public List<PostDTO> findListWithLike() {
        List<PostDTO> postList = postMapper.findListWithLike();
        for (int i = 0; i < postList.size(); i++) {
             postList.get(i).setLikeLists();

        }
    }
}
