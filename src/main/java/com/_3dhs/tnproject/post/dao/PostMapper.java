package com._3dhs.tnproject.post.dao;


import com._3dhs.tnproject.post.dto.AttachmentDTO;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.dto.TabSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface PostMapper {
    List<PostDTO> findListByParam(Map<String, Integer> params); //TODO params 값 String으로 가독성 좋게 리펙터링 할것

    List<AttachmentDTO> findAttListByPostCode(int postCode);

    List<PostDTO> findLikeListPostByMemberCode(int memberCode);

    void updateFolders(List<FolderDTO> requestBody);

    List<FolderDTO> findFolderList(int memberCode);

    void insertAddDefaultFolder(List<FolderDTO> addDefaultFolders);

    PostDTO findPostByPostCode(int postCode);

    List<PostDTO> findAllPostListForDoc();

    List<PostDTO> findListByPostCodes(Set<Integer> postCodes);

    PostDTO findPostLikeCount(int memberCode);

    List<PostDTO> findPostList(TabSearchDTO tabSearchDTO);


}
