package com._3dhs.tnproject.member.dao;

import com._3dhs.tnproject.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

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
}
