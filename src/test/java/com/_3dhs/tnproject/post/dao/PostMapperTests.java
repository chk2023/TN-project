package com._3dhs.tnproject.post.dao;

import com._3dhs.tnproject.post.dto.LikeListDTO;
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
    void findLikeListsByCode_테스트() {
        int rdCode = (int)(Math.random()*10) +1;
        System.out.println(rdCode);
        List<LikeListDTO> dtoList = mapper.findLikeListByCode(rdCode);
        dtoList.forEach(System.out::println);
        assertNotNull(dtoList);
    }
}
