package com._3dhs.tnproject.timeline;

import com._3dhs.tnproject.TnProjectApplication;
import com._3dhs.tnproject.timeline.controller.Recommend;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = {TnProjectApplication.class})
public class RecommendTests {
    @Autowired
    private Recommend recommend;

    @Test
    void 좋아요리스트가_잘_가공되는지_테스트() {
        recommend.setMemberCollaborativeFiltering();
    }

    @BeforeEach
    public void beforeAll() {
        recommend.setMemberCollaborativeFiltering();
    }

    @Test
    void 유저1번과_2번의_유사도비교() {
        double rate = recommend.calculateSimilarity(1, 2);
    }

    @Test
    void 유저1번에게_글추천하기() {
        List<Integer> postCodeList = recommend.recommendPosts(1);
        System.out.println("유저 1번에게 추천된 글은...");
        postCodeList.forEach(System.out::println);


    }
}
