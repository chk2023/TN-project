package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.common.paging.Pagenation;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.manager.service.ReportService;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import jakarta.servlet.ServletOutputStream;
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


    @PostMapping("/manager/report/admUpdate")
    public String updateReport(ReportDTO reportDTO, RedirectAttributes rttr) {

        // 신고 목록 상세-완료 버튼-서브밋 기능

        reportService.updateReport(reportDTO);


        //저장이 잘됐으면 저장 확인 얼럿을 띄워준다

        //서브밋이 결과에 따라 알럿창을 띄워주는 기능을 개발해야함.

        rttr.addFlashAttribute("insertRecord");
        return "redirect:/manager/report/list";


    }

    @PostMapping("/manager/report/memStop")
    public String memberStop(String memberId, Integer reportCode, RedirectAttributes rttr) {
        log.info(memberId, reportCode);

        // 신고 목록 상세-계정정지-서브밋 기능

        reportService.memberStop(memberId);


        rttr.addFlashAttribute("memberStop");

        return "redirect:/manager/report/detail?reportCode=" + reportCode;

    }


    @PostMapping("/manager/report/memActive")
    public String memberActivate(String memberId, Integer reportCode, RedirectAttributes rttr) {
        //계정 정지 해제 서브밋 기능
        System.out.println("memActive실행");
        reportService.memberActivate(memberId);
        rttr.addFlashAttribute("memberActivate");

        return "redirect:/manager/report/detail?reportCode=" + reportCode;
    }


    @PostMapping("/manager/admin/memStop")
    public String admMemberStop(String memberId, Integer reportCode, RedirectAttributes rttr) {
        System.out.println("memstop 실행");
        System.out.println("reportCode : " + reportCode);
        System.out.println("memberId : " + memberId);
        // 관리기록  상세-계정정지-서브밋 기능

        reportService.admMemberStop(memberId);

        rttr.addFlashAttribute("memberStop");

        return "redirect:/manager/admin/detail?reportCode=" + reportCode;

    }

    @PostMapping("/manager/admin/memActive")
    public String admMemberActivate(String memberId, Integer reportCode, RedirectAttributes rttr) {
        //계정 정지 해제 서브밋 기능
        System.out.println("memActive실행");
        reportService.admMemberActivate(memberId);
        rttr.addFlashAttribute("memberActivate");

        return "redirect:/manager/admin/detail?reportCode=" + reportCode;
    }

    @PostMapping("/manager/member/memStop")
    public String MemMemberStop(String memberId, Integer memberCode, RedirectAttributes rttr) {
        System.out.println("memstop 실행");

        System.out.println("memberId : " + memberId);
        // 관리기록  상세-계정정지-서브밋 기능

        reportService.MemMemberStop(memberId);

        rttr.addFlashAttribute("memberStop");

        return "redirect:/manager/member/detail?memberCode=" + memberCode;

    }

    @PostMapping("/manager/member/memActive")
    public String memMemberActivate(String memberId, Integer memberCode, RedirectAttributes rttr) {
        //계정 정지 해제 서브밋 기능
        System.out.println("memActive실행");
        System.out.println("memberCode : " +memberCode);
        System.out.println("memberId : " + memberId);
        reportService.MemMemberActivate(memberId);

        System.out.println("서비스끝나고 1");
        rttr.addFlashAttribute("memMemberActivate");
        System.out.println("서비스끝나고 2");
        return "redirect:/manager/member/detail?memberCode=" + memberCode;
    }

}