package com._3dhs.tnproject.member.service;

import com._3dhs.tnproject.common.exceptionhandler.member.MemberRegistException;
import com._3dhs.tnproject.member.dao.MemberMapper;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    public void registMember(MemberDTO member) throws MemberRegistException {
        int result = memberMapper.insertMember(member);

        if (!(result > 0)) throw new MemberRegistException("회원 가입에 실패하였습니다.");
    }
}
