package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final CommentsMapper commentsMapper;

    @Transactional(readOnly = true)
    public List<PostDTO> findListByParam(Map<String, Integer> params) {
        return postMapper.findListByParam(params);
    }

    @Transactional(readOnly = true)
    public List<PostDTO> findLikeListPostByMemberCode(int memberCode) {
        return postMapper.findLikeListPostByMemberCode(memberCode);
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

    public PostDTO findPostByPostCode(Integer postCode) {
        return postMapper.findPostByPostCode(postCode);
    }

    public List<PostDTO> findAllPostListForDoc() {
        return postMapper.findAllPostListForDoc();
    }

    public List<PostDTO> findListByPostCodes(Set<Integer> postCodes) {
        return postMapper.findListByPostCodes(postCodes);
    }

    @Transactional
    public List<PostDTO> findPostList(TabSearchDTO tabSearchDTO) {
        return postMapper.findPostList(tabSearchDTO);
    }

    public List<AttachmentDTO> findAttListByPostCode(int postCode) {
        return postMapper.findAttListByPostCode(postCode);
    }

    @Transactional
    public boolean isFixedPost(int memberCode) {
        boolean isFixed = postMapper.isFixedPost(memberCode);
        return isFixed;
    }

    @Transactional
    public int addWritePost(PostDTO postDTO) {
        postMapper.addWritePost(postDTO);
        return postDTO.getPostCode(); // ID 반환
    }

    @Transactional
    public void addWriteAttachments(List<AttachmentDTO> attachments) {
        if (attachments != null && !attachments.isEmpty()) {
            postMapper.insertAttachments(attachments);
        }
    }

    @Transactional
    public void addWriteTags(List<TagDTO> tags) {
        if (tags != null && !tags.isEmpty()) {
            postMapper.insertTags(tags);
        }
    }

    @Transactional
    public int addWriteTag(TagDTO tag) {
        postMapper.insertTag(tag);  // DB에 태그를 삽입
        return tag.getTagCode();    // 생성된 태그 ID 반환
    }

    @Transactional
    public void addWritePostTag(PostTagDTO postTag) {
        postMapper.insertPostTag(postTag); // DB에 포스트 태그 관계를 삽입
    }

    @Transactional
    public void addWritePostWithAttachmentsAndTags(PostDTO postDTO, List<AttachmentDTO> attachments, List<TagDTO> tags) {
        int postCode = addWritePost(postDTO); // 포스트를 먼저 저장하고 ID를 받아옴
        attachments.forEach(a -> a.setPostCode(postCode)); // 포스트 코드 설정
        addWriteAttachments(attachments); // 첨부 파일 일괄 삽입

        tags.forEach(t -> {
            int tagCode = addWriteTag(t); // 태그 저장 후 ID 받기
            addWritePostTag(new PostTagDTO(postCode, tagCode)); // 포스트 태그 연결 정보 저장
        });
    }
}
