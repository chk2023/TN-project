package com._3dhs.tnproject.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class WriteDTO {
    private PostDTO postDTO;
    private PostTagDTO postTagDTO;
    private AttachmentDTO attachmentDTO;

    private List<AttachmentDTO> attachmentDTOList;
    private List<TagDTO> tagDTOList;

}
