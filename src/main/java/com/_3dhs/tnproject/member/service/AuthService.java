package com._3dhs.tnproject.member.service;

import com._3dhs.tnproject.member.dao.MemberMapper;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.info("memberId");

        MemberDTO member = memberMapper.findByMemberId(memberId);

        log.info("member : {}", member);

        if (member == null) throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");

        /* 사용자의 탈퇴 여부를 검사하여 탈퇴한 경우 예외를 던진다. */
        if (Boolean.TRUE.equals(member.getIsDeleted())) {
            throw new UsernameNotFoundException("사용자가 삭제되었습니다.");
        }

        //권한 없음

        return member;
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // 아이디 반환
        } else {
            return null;
        }
    }

}
