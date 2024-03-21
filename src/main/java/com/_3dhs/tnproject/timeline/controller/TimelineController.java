package com._3dhs.tnproject.timeline.controller;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timeline")
@Slf4j
public class TimelineController {
    private final PostService service;

    @GetMapping("/trendlist")
    public void findTrendList(Model model) {
        List<PostDTO> trendList = service.findListWithLike();
        //TODO VIEW 완성 안됨

        model.addAttribute("trendList", trendList);


    }
}
