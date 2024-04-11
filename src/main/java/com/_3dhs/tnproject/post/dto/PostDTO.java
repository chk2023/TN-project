package com._3dhs.tnproject.post.dto;

import com._3dhs.tnproject.member.dto.ProfileDTO;
import com._3dhs.tnproject.post.model.PostState;
import com._3dhs.tnproject.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PostDTO {
    private int postCode;
    private String postTitle;
    private String postText;
    private String postStatus;
    private int postPrice;
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
        } else path = "/images/icon_no_image_sm.png";
        return path;
    }

    //TODO 파일첨부해서 글 inset후 바로 메인에서 파일 경로가 앞에 "/"슬래시가 빠져서 오류날떄가 있음 ㅠㅠ
    public void makeThumbnailPath() {
        thumbnailPath = getAttachmentPath(0);
    }
}
