package com._3dhs.tnproject.timeline.service;

import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.timeline.dao.TimelineMapper;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class TimelineServiceTests {

    @Autowired
    private TimelineMapper mapper;

    @Test
    void 의존성_주입_테스트() {
        assertNotNull(mapper);
    }

    @Test
    void 인기글리스트_조회테스트() {
        List<PostDTO> lists = mapper.findTrendList();
//        lists.forEach(System.out::println);
        for (PostDTO list : lists) {
            if (list.getLikeLists() != null) {
                System.out.println(list.getLikeLists());
            } else {
                System.out.println("null");
            }
        }
        assertNotNull(lists);
    }
}
