package com._3dhs.tnproject.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileDTO {
    private int memberCode;
    private int profileCode;
    private String profilePhotoPath;
    private String profileNickname;
    private String profileStatmsg;
    private String profileBgPath;
}
