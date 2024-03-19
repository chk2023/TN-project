package com._3dhs.tnproject.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FolderDTO {
    private int folderCode;
    private String folderName;
    private String folderIconPath;
    private int folderSequence;
}
