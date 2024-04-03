package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.member.dto.ProfileDTO;
import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final CommentsMapper commentsMapper;

    @Transactional(readOnly = true)
    public List<PostDTO> findListByParam(Map<String,Integer> params) {
        List<PostDTO> postList = postMapper.findListByParam(params);
        for (int i = 0; i < postList.size(); i++) {
            postList.get(i).setAttachmentList(postMapper.findAttListByPostCode(postList.get(i).getPostCode()));
            postList.get(i).makeThumbnailPath();
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public List<PostDTO> findLikeListPostByMemberCode(int memberCode) {
        List<PostDTO> postList = postMapper.findLikeListPostByMemberCode(memberCode);
        for (int i = 0; i < postList.size(); i++) {
            postList.get(i).setAttachmentList(postMapper.findAttListByPostCode(postList.get(i).getPostCode()));
            postList.get(i).makeThumbnailPath();
        }
        return postList;
    }

    @Transactional()
    public void updateFolders(List<FolderDTO> requestBody) {
        System.out.println("폴더리스트" + requestBody);
        postMapper.updateFolders(requestBody);
    }

    @Transactional
    public List<FolderDTO> findFolderList(int memberCode) {
        return postMapper.findFolderList(memberCode);
    }

    @Transactional
    public void addDefaultFolder(List<FolderDTO> addDefaultFolders) {
        postMapper.insertAddDefaultFolder(addDefaultFolders);
    }

    @Transactional
    public PostDTO findPostLikeCount(int memberCode) {
        return postMapper.findPostLikeCount(memberCode);
    }

    @Transactional
    public List<PostDTO> findPostList(int memberCode, String tabMenu) {
        return postMapper.findPostList(memberCode, tabMenu);
    }

}
