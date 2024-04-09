package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.post.dao.LikeMapper;
import com._3dhs.tnproject.member.dto.ProfileDTO;
import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.AttachmentDTO;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.dto.TabSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final LikeMapper likeMapper;
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

        /* 유료글결제 추가 */
        Map<String, Integer> params = new HashMap<>();
        params.put("memberCode", memberCode);

//        List<PostDTO> postList = postMapper.findLikeListPostByMemberCode(memberCode);
        List<PostDTO> postList = postMapper.findListByParam(params);
        for (int i = 0; i < postList.size(); i++) {
            postList.get(i).setAttachmentList(postMapper.findAttListByPostCode(postList.get(i).getPostCode()));
            postList.get(i).makeThumbnailPath();
        }
        return postList;
    }

    public PostDTO getPostByPostCode(Integer postCode) {
        PostDTO postDTO = postMapper.findPostByPostCode(postCode);

        List<AttachmentDTO> attachmentList = postMapper.findAttListByPostCode(postCode);
        postDTO.setAttachmentList(attachmentList);

        postDTO.makeThumbnailPath();

        return postDTO;
    }

    @Transactional()
    public void updateFolders(List<FolderDTO> requestBody) {
        System.out.println("폴더리스트" + requestBody);
        postMapper.updateFolders(requestBody);
    }

    @Transactional
    public List<FolderDTO> findFolderList(int memberCode) {
        //System.out.println("코드 잘 넘어옴? : " + memberCode);
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


    public PostDTO findPostByPostCode(Integer postCode) {
        return postMapper.findPostByPostCode(postCode);
    }

    public List<PostDTO> findAllPostListForDoc() {
        return postMapper.findAllPostListForDoc();
    }

    public List<PostDTO> findListByPostCodes(Set<Integer> postCodes) {
        return postMapper.findListByPostCodes(postCodes);
    }

    /* 해당 글에 좋아요를 눌렀는지 확인 */
    public boolean getHasLiked(int postCode, int memberCode) {

        return likeMapper.getHasLiked(postCode, memberCode);
    }

    public boolean toggleLike(int postCode, int memberCode) {
        try {
            boolean isLiked = likeMapper.getHasLiked(postCode, memberCode);
            if (isLiked) {
                likeMapper.cancelLike(postCode, memberCode);
            } else {
                LikeListDTO likeListDTO = new LikeListDTO(postCode, memberCode, false);
                likeMapper.addLike(likeListDTO);
            }
            return !isLiked;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Transactional
    public List<PostDTO> findPostList(TabSearchDTO tabSearchDTO) {
        return postMapper.findPostList( tabSearchDTO);
    }

    public List<AttachmentDTO> findAttListByPostCode(int postCode) {
        return postMapper.findAttListByPostCode(postCode);
    }
}
