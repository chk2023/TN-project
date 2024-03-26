package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final CommentsMapper commentsMapper;

    @Transactional(readOnly = true)
    public List<PostDTO> findListWithLike(int index, int range) {
        List<PostDTO> postList = postMapper.findListWithLike(index,range);
        for (int i = 0; i < postList.size(); i++) {
            postList.get(i).setAttachmentList(postMapper.findAttListByPostCode(postList.get(i).getPostCode()));
            postList.get(i).makeThumbnailPath();
            postList.get(i).setLikeCountStr();
            postList.get(i).setCmtCountStr();
        }
        return postList;
    }
}
