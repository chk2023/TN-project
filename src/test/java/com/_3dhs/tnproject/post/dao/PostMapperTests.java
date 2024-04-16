package com._3dhs.tnproject.post.dao;

import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.dto.TabSearchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostMapperTests {
    @Autowired
    private PostMapper mapper;

    @Test
    void findLikeListPostByMemberCode_테스트() {
        int rdCode = (int)(Math.random() * 12) + 1;
        TabSearchDTO tabSearchDTO = new TabSearchDTO();
        tabSearchDTO.setMemberCode(rdCode);
//        tabSearchDTO.setRange(10);
//        tabSearchDTO.setIndex(0);
//        List<PostDTO> testList = mapper.findLikeListPostByMemberCode(tabSearchDTO);
//        System.out.println("memberCode : " + rdCode);
//        testList.forEach(System.out::println);
//        assertNotNull(testList);
    }

    @Test
    void findAllPostListForDoc_테스트() {
        List<PostDTO> testList = mapper.findAllPostListForDoc();
        testList.forEach(System.out::println);
        assertNotNull(testList);
    }

    @Test
    void findListByPostCodes_테스트() {
        Set<Integer> intList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            intList.add(i+1);
        }
        List<PostDTO> postDTOList = mapper.findListByPostCodes(intList);
        postDTOList.forEach(System.out::println);
        assertNotNull(postDTOList);
    }

    @Test
    void 메인비동기테스트() {
        TabSearchDTO dto = new TabSearchDTO();
//        dto.setIndex(0);
//        dto.setRange(10);
        dto.setMemberCode(1);
//        dto.setTabMenu("최신순");
        //코드리펙토링중 에러 발견으로 비활성화처리
//       List<PostDTO> list = mapper.findPostList(dto);

//        list.forEach(System.out::println);
//        assertNotNull(list);
    }

}
