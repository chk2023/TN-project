package com._3dhs.tnproject.post.dto;

import com._3dhs.tnproject.post.model.PostState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PostDTO {
    private int postCode;
    private String PostTitle;
    private String PostText;
    private PostState postState;
    private  int postPrice;
    private LocalDateTime postWriDate;
    private int postView;
    private int memberCode;
    private LocalDateTime postModDate;
    private LocalDateTime postDeleDate;
    private boolean isDeleted;
    private int folderCode;

}
