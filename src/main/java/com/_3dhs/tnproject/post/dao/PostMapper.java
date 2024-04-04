package com._3dhs.tnproject.post.dao;


import com._3dhs.tnproject.post.dto.AttachmentDTO;
import com._3dhs.tnproject.post.dto.FolderDTO;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface PostMapper {

    LikeListDTO findLikeListByPostAndMemberCode(int postCode, int memberCode);

    List<PostDTO> findListByParam(Map<String ,Integer> params);

    List<LikeListDTO> findLikeListByCode(int postCode);

    List<AttachmentDTO> findAttListByPostCode(int postCode);

    List<PostDTO> findLikeListPostByMemberCode(int memberCode);

    void updateFolders(List<FolderDTO> requestBody);

    List<FolderDTO> findFolderList(int memberCode);

    void insertAddDefaultFolder(List<FolderDTO> addDefaultFolders);

    PostDTO findPostByPostCode(int postCode);

    List<PostDTO> findAllPostListForDoc();

    List<PostDTO> findListByPostCodes(Set<Integer> postCodes);
}
