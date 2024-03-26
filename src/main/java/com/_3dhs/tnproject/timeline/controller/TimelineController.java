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
        model.addAttribute("viewType", viewType);
        return "timeline/trendlist";
    }

    @ResponseBody
    @GetMapping("/trendUpdate")
    public List<PostDTO> findTrendListByIndex(int index, int range) {
        List<PostDTO> postList = service.findListWithLike(index,range);
        return postList;
    }
}
