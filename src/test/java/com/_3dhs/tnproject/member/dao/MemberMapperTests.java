package com._3dhs.tnproject.member.dao;

import com._3dhs.tnproject.member.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
