package com._3dhs.tnproject.post.service;


import com._3dhs.tnproject.comments.dao.CommentsMapper;
import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

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
        try {
            postMapper.addWritePost(postDTO);
            return postDTO.getPostCode(); // ID 반환
        } catch (Exception e) {
            logger.error("addWritePost insert 작업 실패", e);
            throw new RuntimeException("ddWritePost insert 작업 실패", e);
        }
    }

    @Transactional
    public void addWriteAttachments(List<AttachmentDTO> attachments, int postCode) {
        if (attachments != null && !attachments.isEmpty()) {
            attachments.forEach(a -> a.setPostCode(postCode)); //포스트 코드 설정
            try {
                postMapper.insertAttachments(attachments);
            } catch (Exception e) {
                logger.error("addWriteAttachments insert 작업 실패 , 설정된 포스트 코드 :  " + postCode, e);
                throw new RuntimeException("addWriteAttachments insert 작업 실패", e);
            }
        }
    }

    @Transactional
    public void addWriteTags(List<TagDTO> tags, int postCode) {
        if (tags != null && !tags.isEmpty()) {
            tags.forEach(t -> handleTagInsertion(t, postCode));
        }
    }

    private void handleTagInsertion(TagDTO tag, int postCode) {
        try {
            int tagCode = addWriteTag(tag); // 태그 1개씩 insert 후 tagCode 받기
            addWritePostTag(new PostTagDTO(postCode, tagCode)); // 위에서 받아온 tagCode 연결 정보로 저장
        } catch (Exception e) {
            logger.error("addWritePostTag 정보 연결 작업 실패 , 받아온 postCode : ", postCode, e);
            throw new RuntimeException("addWritePostTag 정보 연결 실패", e);
        }
    }

    @Transactional
    public int addWriteTag(TagDTO tag) {
        try {
            postMapper.insertTag(tag);  // DB에 태그를 삽입
            return tag.getTagCode();    // 생성된 tagCode 반환
        } catch (Exception e) {
            logger.error("addWriteTag insert 작업 실패 (각각의 태그 하나씩)", e);
            throw new RuntimeException("addWriteTag insert 작업 실패 (각각의 태그 하나씩)", e);
        }
    }

    @Transactional
    public void addWritePostTag(PostTagDTO postTag) {
        try {
            postMapper.insertPostTag(postTag);// DB에 포스트 태그 관계를 삽입
        } catch (Exception e) {
            logger.error("addWritePostTag insert 작업 실패", e);
            throw new RuntimeException("addWritePostTag insert 작업 실패", e);
        }
    }

    @Transactional
    public void addWritePostWithAttachmentsAndTags(PostDTO postDTO, List<AttachmentDTO> attachments, List<TagDTO> tags) {
        try {
            int postCode = addWritePost(postDTO); // 포스트를 먼저 저장하고 생성된 postCode를 받아옴
            addWriteAttachments(attachments, postCode); // 첨부 파일 일괄 삽입
            addWriteTags(tags, postCode); // 태그 일괄 삽입
        } catch (Exception e) {
            logger.error("Failed to write post with attachments and tags", e);
            throw new RuntimeException("Failed to write post with attachments and tags", e);
        }
    }
}
