package com._3dhs.tnproject.member.dao;

import com._3dhs.tnproject.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberId(String memberId);

    int insertMember(MemberDTO member);

    String selectMemberById(String memberId);

    int updateMember(MemberDTO updateMember);

    void updateTissue(String memberId, int nTissue);

}
