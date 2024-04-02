package com._3dhs.tnproject.member.service;

import com._3dhs.tnproject.common.exceptionhandler.member.MemberRemoveException;
import com._3dhs.tnproject.common.exceptionhandler.member.MemberUpdateException;
import com._3dhs.tnproject.common.exceptionhandler.member.MemberRegistException;
import com._3dhs.tnproject.member.dao.MemberMapper;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        int result2 = memberMapper.insertProfile(member.getMemberId());

        if (result <= 0 || result2 <= 0) throw new MemberRegistException("회원 가입에 실패하였습니다.");
    }

    // 결제 추가
//    public void updateHaveTissue(String memberId, int nTissue) {
//        memberMapper.updateTissue(memberId, nTissue);
//    }

    public MemberDTO getMemberbyId(String userId) {
        return memberMapper.findByMemberId(userId);
    }

    public void updateHaveTissue(MemberDTO memberDTO) {
        memberMapper.updateTissue(memberDTO.getMemberId(), memberDTO.getHaveTissue());


    }


    @Transactional
    public void updateMember(MemberDTO updateMember) throws MemberUpdateException {

        int result = memberMapper.updateMember(updateMember);

        if (!(result > 0)) throw new MemberUpdateException("회원 정보 수정에 실패하였습니다.");

    }

    public void deleteMember(MemberDTO member) throws MemberRemoveException {
        int result = memberMapper.deleteMember(member);

        if (!(result > 0)) {
            throw new MemberRemoveException("회원 탈퇴에 실패하였습니다.");
        }
    }
}
