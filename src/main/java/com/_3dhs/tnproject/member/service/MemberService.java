package com._3dhs.tnproject.member.service;

import com._3dhs.tnproject.common.exceptionhandler.member.MemberUpdateException;
import com._3dhs.tnproject.common.exceptionhandler.member.MemberRegistException;
import com._3dhs.tnproject.member.dao.MemberMapper;
import com._3dhs.tnproject.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public boolean selectMemberById(String memberId) {
        String result = memberMapper.selectMemberById(memberId);

        return result != null;
    }

    @Transactional
    public void registMember(MemberDTO member) throws MemberRegistException {
        int result = memberMapper.insertMember(member);

        if (!(result > 0)) throw new MemberRegistException("회원 가입에 실패하였습니다.");
    }

    @Transactional
    public void updateMember(MemberDTO updateMember) throws MemberUpdateException {

        int result = memberMapper.updateMember(updateMember);

        if (!(result > 0)) throw new MemberUpdateException("회원 정보 수정에 실패하였습니다.");

    }
}
