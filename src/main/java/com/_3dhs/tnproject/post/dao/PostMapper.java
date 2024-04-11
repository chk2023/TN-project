package com._3dhs.tnproject.post.dao;


import com._3dhs.tnproject.post.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface PostMapper {

    List<PostDTO> findListByParam(Map<String, Integer> params);

    List<LikeListDTO> findLikeListByCode(int postCode);

    List<AttachmentDTO> findAttListByPostCode(int postCode);

    List<PostDTO> findLikeListPostByMemberCode(int memberCode);

    void updateFolders(List<FolderDTO> requestBody);

    List<FolderDTO> findFolderList(int memberCode);

    void insertAddDefaultFolder(List<FolderDTO> addDefaultFolders);

    PostDTO findPostByPostCode(int postCode);

    List<PostDTO> findAllPostListForDoc();

    List<PostDTO> findListByPostCodes(Set<Integer> postCodes);

    TabSearchDTO findPostLikeCount(int memberCode, boolean isOwner);

    List<PostDTO> findPostList(TabSearchDTO tabSearchDTO);

    boolean isFixedPost(int memberCode);

    void addWritePost(PostDTO postDTO);

    void insertAttachments(List<AttachmentDTO> attachments);

    void insertTags(List<TagDTO> tags);

    void insertTag(TagDTO tag);

    void insertPostTag(PostTagDTO postTag);

    TabSearchDTO selectTotalCount(int memberCode, boolean isOwner);

    List<PostDTO> findAllPostList(Map<String, Object> parameters);

    List<PostDTO> findPublicPostList(Map<String, Object> parameters);

    List<PostDTO> findAllFolderPostList(Map<String, Object> parameters);

    List<PostDTO> findPublicFolderPostList(Map<String, Object> parameters);

    TabSearchDTO selectFolderTotalCount(int folderCode, int memberCode, boolean isOwner);

    TabSearchDTO selectPublicFolderTotalCount(int folderCode, int memberCode, boolean isOwner);

}
