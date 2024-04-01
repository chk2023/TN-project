package com._3dhs.tnproject.member.dto;

import com._3dhs.tnproject.member.model.Authority;
import com._3dhs.tnproject.member.model.MemberGender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberDTO implements UserDetails {
    private int memberCode;
    private String memberId;
    private String memberPwd;
    private MemberGender memberGender;
    private int memberAge;
    private LocalDate memberBirth;
    private LocalDateTime memberSubDate;
    private String memberStatus;
    private int haveTissue;
    private Authority memberAuthority;
    private LocalDateTime dormantTransDate;
    private LocalDateTime suspendTransDate;
    private LocalDateTime deleteTransDate;
    private boolean isDeleted;
    private ProfileDTO profile;
    private LocalDateTime deleteTransDate;
    private Boolean isDeleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = "";
        if(memberAuthority != null) roleName = memberAuthority.name();
        return Arrays.asList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return memberPwd;
    }

    @Override
    public String getUsername() {
        return memberId;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
