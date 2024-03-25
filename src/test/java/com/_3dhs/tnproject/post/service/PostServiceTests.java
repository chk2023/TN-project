package com._3dhs.tnproject.post.service;

import com._3dhs.tnproject.post.dto.PostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class PostServiceTests {
    @Autowired
    private PostService postService;

    @Test
    void 의존성_주입_테스트() {
        assertNotNull(postService);
    }

    @Test
    void 인기글리스트_조회테스트() {
        List<PostDTO> postDTOList = postService.findListWithLike(index, range);
        for (int i = 0; i < postDTOList.size(); i++) {
            System.out.println("======="+postDTOList.get(i).getPostCode()+"=======");
            System.out.println("likeCount");
            System.out.println(postDTOList.get(i).getLikeCount());
            postDTOList.get(i).getAttachmentList().forEach(System.out::println);
            System.out.println(postDTOList.get(i).getAttachmentPath(0));
            System.out.println("닉네임 : " + postDTOList.get(i).getProfile().getProfileNickname());
        }
        assertNotNull(postDTOList);
    }


}
