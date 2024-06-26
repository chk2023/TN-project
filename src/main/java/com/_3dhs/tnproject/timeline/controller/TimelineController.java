package com._3dhs.tnproject.timeline.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.LikeService;
import com._3dhs.tnproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timeline")
@Slf4j
public class TimelineController {
    private final PostService postService;
    private final Recommend recommend;

    @GetMapping("/list")
    public String findTrendList(Model model, String viewType,Integer postCode, Integer contentsType, Authentication authentication) {
        Integer index = 0;
        Integer range = 10;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        model.addAttribute("index", index);
        model.addAttribute("range", range);
        model.addAttribute("viewType", viewType);
        model.addAttribute("contentsType", contentsType);
        model.addAttribute("today", today);

        model.addAttribute("postCode", postCode);

        return "timeline/list";
    }

    @ResponseBody
    @GetMapping("/updateList")
    public List<PostDTO> findListByParam(Integer index, Integer range, Integer contentsType, @AuthenticationPrincipal MemberDTO member) {
        List<PostDTO> list = new ArrayList<>();
        Map<String, Integer> params = new HashMap<>();
        params.put("index", index);
        params.put("range", range);
        params.put("contentsType", contentsType);
        switch (contentsType) {
            case 3:
                if (index < 10) {
                    Set<Integer> postCodeList = recommend.recommendPosts(member.getMemberCode());
                    if (postCodeList.size() < 0) {
                        break;
                    }
                    list = postService.findListByPostCodes(postCodeList);
                } else{
                    break;
                }
                break;
            default:
                list = postService.findListByParam(params);
                break;
        }
        return list;
    }
}
