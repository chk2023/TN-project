package com._3dhs.tnproject.post.service;

import com._3dhs.tnproject.TnProjectApplication;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.dto.TabSearchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {TnProjectApplication.class})
public class PostServiceTests {
    @Autowired
    private PostService postService;

    @Test
    void 의존성_주입_테스트() {
        assertNotNull(postService);
    }

    @Test
    @WithMockUser(username="testUser", roles={"ADMIN"})
    void findLikeListPostByMemberCode_테스트() {
        // 유저 12번이 좋아요 한 글의 수는 18개이다.
        // DB조건에 따라 다른 결과가 나올 수 있다.
        TabSearchDTO tabSearchDTO = new TabSearchDTO();
        tabSearchDTO.setMemberCode(12);
        tabSearchDTO.setRange(20);
        tabSearchDTO.setIndex(0);
        int result = postService.findLikeListPostByMemberCode(tabSearchDTO).size();
        assertEquals(result, 18);
    }


}
