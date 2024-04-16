package com._3dhs.tnproject.post.dao;


import com._3dhs.tnproject.post.dto.*;
import com._3dhs.tnproject.post.model.PostUpdateModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface PostMapper {

    List<PostDTO> findListByParam(PostUpdateModel postUpdateModel);

    List<AttachmentDTO> findAttListByPostCode(int postCode);

    List<PostDTO> findLikeListPostByMemberCode(PostUpdateModel postUpdateModel);
    PostDTO findPostByPostCode(int postCode);

    List<PostDTO> findAllPostListForDoc();

    List<PostDTO> findListByPostCodes(Set<Integer> postCodes);

    TabSearchDTO findPostLikeCount(int memberCode, boolean isOwner);

    List<PostDTO> findPostList(PostUpdateModel postUpdateModel);

    boolean isFixedPost(int memberCode);

    void addWritePost(PostDTO postDTO);

    void insertAttachments(List<AttachmentDTO> attachments);

    void insertTag(TagDTO tag);

    void insertPostTag(PostTagDTO postTag);

    TabSearchDTO selectTotalCount(int memberCode, boolean isOwner);

    List<PostDTO> findAllPostList(Map<String, Object> parameters);

    List<PostDTO> findPublicPostList(Map<String, Object> parameters);

    List<PostDTO> findAllFolderPostList(Map<String, Object> parameters);

    List<PostDTO> findPublicFolderPostList(Map<String, Object> parameters);

    TabSearchDTO selectFolderTotalCount(int folderCode, int memberCode, boolean isOwner);

    TabSearchDTO selectPublicFolderTotalCount(int folderCode, int memberCode, boolean isOwner);

    List<TagDTO> getTagsByPostCode(int postCode);

    PostDTO findLastInsertPost();

    List<PostDTO> findDraftPostList(Map<String, Object> parameters);

    TabSearchDTO findDraftPostCount(int memberCode);
}
