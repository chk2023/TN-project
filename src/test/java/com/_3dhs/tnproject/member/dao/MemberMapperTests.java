package com._3dhs.tnproject.member.dao;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.model.Authority;
import com._3dhs.tnproject.member.model.MemberGender;
import com._3dhs.tnproject.member.model.MemberStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class MemberMapperTests {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    void ID로맴버찾기_테스트() {
       MemberDTO dto = memberMapper.findByMemberId("1");
        if (dto != null) {
            System.out.println(dto.getMemberId());
            System.out.println(dto.getProfile());
        }

        assert (dto != null);
    }

    @Test
    void 회원가입테스트() {
        MemberDTO dto = new MemberDTO();
        dto.setMemberId("testID");
        dto.setMemberPwd("testPwd");
        dto.setMemberGender(MemberGender.MALE);
        dto.setMemberAge(15);
        dto.setMemberBirth(LocalDate.of(2020,12,12));
        memberMapper.insertMember(dto);
        System.out.println();
    }

    @Test
    void findMemberByMemberCodes_테스트() {
        List<Integer> rdIntList = new ArrayList<>();
        System.out.println("=============================랜덤번호 추출");
        for (int i = 0; i < 3; i++) {
            int rdcode = (int)(Math.random()*12) +1;
            rdIntList.add(rdcode);
            System.out.println(rdcode);
        }
        System.out.println("추출 끝");
        List<MemberDTO> memberDTOS = memberMapper.findMemberByMemberCodes(rdIntList);
        memberDTOS.forEach(memberDTO -> {
            System.out.println(memberDTO.getProfile().getMemberCode());
        });
    }
}
