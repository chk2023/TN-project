package com._3dhs.tnproject.member.dto;

import com._3dhs.tnproject.member.model.Authority;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MemberDTO {
    private int memberCode;
    private String memberId;
    private String memberPwd;
    private String memberGender;
    private int memberAge;
    private LocalDateTime memberBirth;
    private LocalDateTime memberSubDate;
    private String memberStatus;
    private int haveTissue;
    private Authority memberAuthority;
    private LocalDateTime dormantTransDate;
    private LocalDateTime suspendTransDate;
}
