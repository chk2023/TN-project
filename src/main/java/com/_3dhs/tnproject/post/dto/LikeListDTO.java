package com._3dhs.tnproject.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class LikeListDTO {
    private int postCode;
    private int memberCode;
    private boolean isPrivate;


}

