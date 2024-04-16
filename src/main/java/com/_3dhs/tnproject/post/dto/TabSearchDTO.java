package com._3dhs.tnproject.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TabSearchDTO {
    private int memberCode;
    private int folderCode;
    private int likeCount;
    private int count;
    private String postStatus;
}
