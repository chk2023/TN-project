package com._3dhs.tnproject.timeline.controller;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Indexed;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timeline")
@Slf4j
public class TimelineController {
    private final PostService service;

    @GetMapping("/list")
    public String findTrendList(Model model, String viewType, Integer contentsType, Authentication authentication) {
        Integer index = 0;
        Integer range = 10;

        model.addAttribute("index", index);
        model.addAttribute("range", range);
        model.addAttribute("viewType", viewType);
        model.addAttribute("contentsType", contentsType);

        return "timeline/list";
    }

    @ResponseBody
    @GetMapping("/updateList")
    public List<PostDTO> findListByParam(Integer index, Integer range, Integer contentsType) {
        Map<String, Integer> params = new HashMap<>();
        params.put("index",index);
        params.put("range", range);
        params.put("contentsType", contentsType);
        return service.findListByParam(params);
    }
}
