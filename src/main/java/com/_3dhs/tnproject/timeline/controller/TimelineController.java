package com._3dhs.tnproject.timeline.controller;
import com._3dhs.tnproject.member.dto.ProfileDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.model.PostState;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timeline")
@Slf4j
public class TimelineController {
    private final PostService service;

    @GetMapping("/trendlist")
    public String findTrendList(Model model, String viewType) {
        int index = 0;
        int range = 10;

        model.addAttribute("index", index);
        model.addAttribute("range", range);
        model.addAttribute("message", "아모른직다");
        model.addAttribute("viewType", viewType);
        return "timeline/trendlist";
    }

    @ResponseBody
    @GetMapping("/trendUpdate")
    public List<PostDTO> findTrendListByIndex(/*int index, int range*/) {
        //테스트
        List<PostDTO> postList = new ArrayList<>();
        PostDTO testPost = new PostDTO(1,
                "테스트 제목",
                "본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글",
                PostState.PUBLIC,
                0,
                LocalDateTime.now(),
                20,
                1,
                LocalDateTime.now(),
                null,
                false,
                0,
                new ProfileDTO(1,1,"닉네임","버그나지말아주세요","/userUploadFiles/cat.jpg",null,null),
                10,
                "10",
                10,
                "10",
                null,
                null,
                "/userUploadFiles/tree.jpg"
        );

        PostDTO testPost2 = new PostDTO(1,
                "테스트 제목22222",
                "본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글본문글",
                PostState.PUBLIC,
                0,
                LocalDateTime.now(),
                20,
                1,
                LocalDateTime.now(),
                null,
                false,
                0,
                new ProfileDTO(1,1,"닉네임","버그나지말아주세요","/userUploadFiles/cat.jpg",null,null),
                10,
                "10",
                10,
                "10",
                null,
                null,
                "/userUploadFiles/tree.jpg"
        );
        postList.add(testPost2);
        postList.add(testPost);
        //테스트코드 END
        return postList;
    }
}
