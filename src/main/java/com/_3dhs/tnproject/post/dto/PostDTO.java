package com._3dhs.tnproject.post.dto;

import com._3dhs.tnproject.member.dto.ProfileDTO;
import com._3dhs.tnproject.post.model.PostState;
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
    private int folderCode;
    private ProfileDTO profile;
    private int likeCount;
    private String likeCountStr;
    private int cmtCount;
    private  String cmtCountStr;
    private List<TagDTO> tagList;
    private List<AttachmentDTO> attachmentList;
    private String thumbnailPath;

    public PostDTO(int postCode, String postTitle, String postText, PostState postState, int postPrice, LocalDateTime postWriDate, int postView, int memberCode, LocalDateTime postModDate, LocalDateTime postDeleDate, boolean isDeleted, int folderCode, ProfileDTO profile, int likeCount, String likeCountStr, int cmtCount, String cmtCountStr, List<TagDTO> tagList, List<AttachmentDTO> attachmentList, String thumbnailPath) {
        this.postCode = postCode;
        this.postTitle = postTitle;
        this.postText = postText;
        this.postState = postState;
        this.postPrice = postPrice;
        this.postWriDate = postWriDate;
        this.postView = postView;
        this.memberCode = memberCode;
        this.postModDate = postModDate;
        this.postDeleDate = postDeleDate;
        this.isDeleted = isDeleted;
        this.folderCode = folderCode;
        this.profile = profile;
        this.likeCount = likeCount;
        this.likeCountStr = likeCountStr;
        this.cmtCount = cmtCount;
        this.cmtCountStr = cmtCountStr;
        this.tagList = tagList;
        this.attachmentList = attachmentList;
        this.thumbnailPath = thumbnailPath;
    }

    public String getAttachmentPath(int index) {
        String path = "";
        if (attachmentList.size() > index) {
            path = attachmentList.get(index).getFilePath();
            path += "/" + attachmentList.get(index).getSafeName();
        } else path = "/image/icon_default_photo.png";
        return path;
    }
    public void setLikeCountStr() {
        String tempS = "";
        if (likeCount >= 1000) {
            double tempD = (double) likeCount * 0.001;
            tempS = Double.toString(Math.floor(tempD)) + "k";
        } else if (likeCount >=100) {
            double tempD =(double)likeCount / 100;
            tempD = Math.floor(tempD);
            int result = (int)tempD *100;
            tempS = Integer.toString(result);
        }
        likeCountStr = tempS;
    }
    public void setCmtCountStr() {
        String tempS = "";
        if (cmtCount >= 1000) {
            double tempD = (double) cmtCount * 0.001;
            tempS = Double.toString(Math.floor(tempD)) + "k";
        } else if (cmtCount >=100) {
            double tempD =(double)cmtCount / 100;
            tempD = Math.floor(tempD);
            int result = (int)tempD *100;
            tempS = Integer.toString(result);
        }
        cmtCountStr = tempS;
    }
    public void makeThumbnailPath() {
       thumbnailPath =  getAttachmentPath(0);
    }
}
