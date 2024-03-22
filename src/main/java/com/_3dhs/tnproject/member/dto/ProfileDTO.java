package com._3dhs.tnproject.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProfileDTO {
    private int memberCode;
    private int profileCode;
    private String profileNickname;
    private String profileStatmsg;
    private String profileImgPath;
    private String profileBgPath;
    private LocalDateTime memberBirth; //member 테이블에 있는 것이지만 post에서 사용하기 용이하도록 지정
}
