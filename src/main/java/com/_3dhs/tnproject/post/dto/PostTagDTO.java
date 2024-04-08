package com._3dhs.tnproject.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PostTagDTO {
    private PostDTO postDTO;
    private TagDTO tagDTO;
}
