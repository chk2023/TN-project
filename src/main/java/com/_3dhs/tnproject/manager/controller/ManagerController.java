package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.common.paging.Pagenation;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.manager.service.ReportService;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerController {

    private final ReportService reportService;
    private final MemberService memberService;
    private final MessageSourceAccessor messageSourceAccessor;
    private final ReportDTO reportDTO;


    @GetMapping("/manager/report/list")
    public String getReportList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(required = false) String searchCondition,
                                @RequestParam(required = false) String searchValue,
                                Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);


        Map<String, Object> boardListAndPaging = reportService.selectReportList(searchMap, page);
        model.addAttribute("paging", boardListAndPaging.get("paging"));
        model.addAttribute("reportList", boardListAndPaging.get("reportList"));

        return "/manager/report/list";
    }

    @GetMapping("/manager/report/detail")
    public String getReportDetail(@RequestParam Integer reportCode, Model model) {
        ReportDTO reportDet = reportService.selectReportDetail(reportCode);
        model.addAttribute("detail", reportDet);

        return "manager/report/detail";

    }

    @GetMapping("/manager/admin/list")
    public String getAdminList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(required = false) String searchCondition,
                               @RequestParam(required = false) String searchValue,
                               Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> boardListAndPaging = reportService.selectAdminList(searchMap, page);
        model.addAttribute("paging", boardListAndPaging.get("paging"));
        model.addAttribute("adminList", boardListAndPaging.get("adminList"));

        return "/manager/admin/list";
    }


    @GetMapping("/manager/admin/detail")
    public String selectAdminDetail(Integer reportCode, Model model) {
        ReportDTO admReport = reportService.selectAdminDetail(reportCode);
        model.addAttribute("admDet", admReport);
        log.info(String.valueOf(reportCode));
        log.info("{}", admReport);
        return "manager/admin/detail";
    }


    @GetMapping("/manager/member/list")
    public String getMemberList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(required = false) String searchCondition,
                                @RequestParam(required = false) String searchValue,
                                Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> boardListAndPaging = reportService.selectMemberList(searchMap, page);
        model.addAttribute("paging", boardListAndPaging.get("paging"));
        model.addAttribute("members", boardListAndPaging.get("memberList"));

        return "/manager/member/list";
    }


    @GetMapping("/manager/member/detail")
    public String selectOneMember(MemberDTO memberDTO, Model model) {
        MemberDTO oneMember = reportService.selectOneMember(memberDTO);
        model.addAttribute("oneMember", oneMember);
        return "manager/member/detail";
    }


    @PostMapping("/manager/report/admUpdate") //getMapping으로 값을 넘길 이유가 없으니까, 포스트 매핑을 시켜도 될 것 같은데..
    public String updateReport(ReportDTO reportDTO, RedirectAttributes rttr) {

        // 신고 목록 상세-완료 버튼-서브밋 기능

        reportService.updateReport(reportDTO);


        //저장이 잘됐으면 저장 확인 얼럿을 띄워준다

        //서브밋이 결과에 따라 알럿창을 띄워주는 기능을 개발해야함.

        rttr.addFlashAttribute("insertRecord");
        return "redirect:/manager/report/list";

    }

    @PostMapping("/manager/report/memStop")
    public String memberStop(MemberDTO memberDTO, Integer reportCode,RedirectAttributes rttr) {

        // 신고 목록 상세-계정정지-서브밋 기능

        reportService.memberStop(memberDTO);


        rttr.addFlashAttribute("memberStop");

        return "redirect:/manager/report/detail?reportCode="+reportCode;


//    @PostMapping("/report/complete")
//    public String compliteReport(RedirectAttributes rttr) {
//        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("report.complete"));
//        return "redirect:/manager/report/detail";
//    }


//
//    @GetMapping("/memActivate")
//    public String memberActivate(ReportDTO reportDTO,RedirectAttributes rttr) {
//
//        return "redirect:/manager/report/list";
//    }
//
//
//

    }
}