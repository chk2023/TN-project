package com._3dhs.tnproject.post.dto;

import com._3dhs.tnproject.member.dto.ProfileDTO;
import com._3dhs.tnproject.post.model.PostState;
import com._3dhs.tnproject.post.service.PostService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class PostDTO {
    private int postCode;
    private String postTitle;
    private String postText;
    private PostState postState;
    private  int postPrice;
    private LocalDateTime postWriDate;
    private int postView;
    private int memberCode;
    private LocalDateTime postModDate;
    private LocalDateTime postDeleDate;
    private boolean isDeleted;
    private boolean postIsFixed;
    private int folderCode;
    private ProfileDTO profile;
    private int likeCount;
    private int cmtCount;
    private List<TagDTO> tagList;
    private List<AttachmentDTO> attachmentList;
    private boolean isLiked;

    //가공하는 자료
    private String thumbnailPath;
    private PostService postService;
    private int postCount;

    public String getAttachmentPath(int index) {
        String path = "";
        if (attachmentList.size() > index) {
            path = attachmentList.get(index).getFilePath();
            path += "/" + attachmentList.get(index).getSafeName();
        } else path = "/images/icon_default_photo.png";
        return path;
    }
    public void makeThumbnailPath() {
       thumbnailPath =  getAttachmentPath(0);
    }

    /* 좋아요 기능 추가 */
    public void setLike(int memberCode) {
        // 해당 게시물이 현재 사용자에 의해 좋아요 되었는지 여부를 확인
        this.isLiked = postService.getHasLiked(this.postCode, memberCode);;

    }
}
