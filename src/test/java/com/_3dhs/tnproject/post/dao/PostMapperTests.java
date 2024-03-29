package com._3dhs.tnproject.post.dao;

import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostMapperTests {
    @Autowired
    private PostMapper mapper;

    @Test
    void findLikeListPostByMemberCode_테스트() {
        int rdCode = (int)(Math.random() * 12) + 1;
        List<PostDTO> testList = mapper.findLikeListPostByMemberCode(rdCode);
        System.out.println("memberCode : " + rdCode);
        testList.forEach(System.out::println);
        assertNotNull(testList);
    }
}
