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
    private int cmtCount;
    private List<TagDTO> tags;
}
