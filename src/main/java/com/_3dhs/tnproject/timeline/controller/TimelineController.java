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
    public String findTrendList(Model model) {
        List<PostDTO> trendList = service.findListWithLike();
        trendList.sort((a,b)-> b.getLikeCount()-a.getLikeCount());


        model.addAttribute("trendList", trendList);
        return "timeline/trendlist";
    }
}
