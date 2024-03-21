package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) { this.postMapper = postMapper; }

    public void addLikeList(LikeListDTO likeList, PostDTO post, MemberDTO member) {

        likeList.setPostCode(post.getPostCode());
        likeList.setMemberCode(member.getMemberCode());
        likeList.setPrivate(false);

        postMapper.addLike(likeList);
    }
}
