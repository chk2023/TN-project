package com._3dhs.tnproject.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachmentDTO {
    private int fileCode;
    private String originName;
    private String safeName;
    private String filePath;
    private int postCode;
}
