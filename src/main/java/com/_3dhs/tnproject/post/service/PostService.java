package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.LikeListDTO;
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

    /* 해당 글에 좋아요를 눌렀는지 확인 */
//    public boolean likePost(LikeListDTO likeListDTO) {

//        likeListDTO existLike =
//                postMapper.findLikeListByPostAndMemberCode(likeListDTO.getPostCode(), likeListDTO.getMemberCode());
//
//        if (existLike != null) {
//            postMapper.cancelLike(likeListDTO.getPostCode(), likeListDTO.getMemberCode());
//            return false;
//        } else {
//            postMapper.addLike(likeListDTO);
//            return true;
//        }
//
////        int isAddLikeSuccessed = postMapper.addLike(likeListDTO);
////
////        return isAddLikeSuccessed > 0;
//    }
//}
