package com._3dhs.tnproject.post.service;

import com._3dhs.tnproject.post.dao.PostMapper;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class PostServiceTests {
    @Autowired
    private PostMapper mapper;

    @Test
    void 의존성_주입_테스트() {
        assertNotNull(mapper);
    }

    @Test
    void 인기글리스트_조회테스트() {
        List<PostDTO> lists = mapper.findListWithLike();
        List<LikeListDTO> likeListDTOS = new ArrayList<>();
//        lists.forEach(System.out::println);
        for (PostDTO list : lists) {
            if (list.getPostCode() == 4) {
                likeListDTOS = list.getLikeLists();
            }
        }
        System.out.println("========테스트로그==============");
        System.out.println(likeListDTOS.size());
        likeListDTOS.forEach(System.out::println);
        assertNotNull(lists);
    }


}
