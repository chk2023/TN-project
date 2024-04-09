package com._3dhs.tnproject.member.service;

import com._3dhs.tnproject.common.exceptionhandler.member.MemberRegistException;
import com._3dhs.tnproject.common.exceptionhandler.member.MemberRemoveException;
import com._3dhs.tnproject.common.exceptionhandler.member.MemberUpdateException;
import com._3dhs.tnproject.member.dao.MemberMapper;
import com._3dhs.tnproject.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
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

    public List<MemberDTO> viewAllMembers(MemberDTO memberDTO) {
        return memberMapper.viewAllMembers(memberDTO);
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

    @Transactional
    public void deleteMember(MemberDTO member) throws MemberRemoveException {
        int result = memberMapper.deleteMember(member);

        if (!(result > 0)) {
            throw new MemberRemoveException("회원 탈퇴에 실패하였습니다.");
        }
    }

    @Transactional
    public void updatePwd(MemberDTO member) throws MemberUpdateException {
        int result = memberMapper.updatePwd(member);

        if (!(result > 0)) throw new MemberUpdateException("회원 정보 수정에 실패하였습니다.");
    }

    public MemberDTO findMainBlogMemberInfo(int memberCode) {
        MemberDTO memberDTO = memberMapper.findMainBlogMemberInfo(memberCode);
        return memberDTO;
    }

    @Transactional
    public void updateProfile(MemberDTO member) throws MemberUpdateException {
        int result = memberMapper.updateProfile(member);

        if (!(result > 0)) throw new MemberUpdateException("회원 정보 수정에 실패하였습니다.");
    }

    @Transactional
    public int blockMemberByMemberCode(int memberCode, int targetMemberCode) {
        int result = 0;
        try {
           result =  memberMapper.blockMemberByMemberCode(memberCode, targetMemberCode);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            result = 999;
        }
        return result;
    }
    @Transactional
    public int unblockMemberByMemberCode(int memberCode, int targetMemberCode) {
        return memberMapper.unblockMemberByMemberCode(memberCode, targetMemberCode);
    }

    public List<Integer> findBlockListByMemberCode(int memberCode) {
        return memberMapper.findBlockListByMemberCode(memberCode);
    }

    public List<MemberDTO> findMemberByMemberCodes(List<Integer> blockedMemberCodeList) {
        return memberMapper.findMemberByMemberCodes(blockedMemberCodeList);
    }


}
