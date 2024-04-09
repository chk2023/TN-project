package com._3dhs.tnproject.member.dao;

import com._3dhs.tnproject.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberId(String memberId);

    int insertMember(MemberDTO member);

    String selectMemberById(String memberId);

    List<MemberDTO> viewAllMembers(MemberDTO memberDTO);

    int updateMember(MemberDTO updateMember);

    void updateTissue(String memberId, int nTissue);

    int deleteMember(MemberDTO member);

    int findLastInsertId();

    int updatePwd(MemberDTO member);
    int insertProfile(String memberId);

    MemberDTO findMainBlogMemberInfo(int memberCode);

    int updateProfile(MemberDTO member);

    int blockMemberByMemberCode(int memberCode, int targetMemberCode) throws SQLIntegrityConstraintViolationException;

    List<Integer> findBlockListByMemberCode(int memberCode);

    List<MemberDTO> findMemberByMemberCodes(List<Integer> blockedMemberCodeList);

    int unblockMemberByMemberCode(int memberCode, int targetMemberCode);
}
