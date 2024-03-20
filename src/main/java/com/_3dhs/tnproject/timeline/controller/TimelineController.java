package com._3dhs.tnproject.timeline.controller;

import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.timeline.service.TimelineService;
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
    private final TimelineService service;

    @GetMapping("/trendlist")
    public void findTrendList(Model model) {
        List<PostDTO> trendList = service.findTrendList();
        trendList.forEach(content -> log.info("DTO : {}",content));

        model.addAttribute("trendList", trendList);


    }
}
