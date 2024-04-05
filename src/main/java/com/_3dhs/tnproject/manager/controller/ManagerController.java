package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.common.paging.Pagenation;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.manager.service.ReportService;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller @Slf4j
@RequiredArgsConstructor
public class ManagerController {

    private final ReportService reportService;
    private final MemberService memberService;
    private final MessageSourceAccessor messageSourceAccessor;



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
        model.addAttribute("detail", reportDet) ;

        return "manager/report/detail";

    }

//    @GetMapping("/manager/report/detail")
//    public String viewOneReport(Model model1, Integer reportCode) {
//        ReportDTO report = reportService.viewOneReport(reportCode);
//        model1.addAttribute("detail", report);
//
//
//        return "manager/report/detail";
//    }


    @PostMapping("/manager/report/detail") //getMapping으로 값을 넘길 이유가 없으니까, 포스트 매핑을 시켜도 될 것 같은데..
    public String updateReport(ReportDTO reportDTO, RedirectAttributes rttr) {

        // 신고 목록 상세에서 내역을 입력해서 완료 버튼을 누르면 해당 내용이 디비에 저장하는 기능

        Integer updateReportCode = reportDTO.getReportCode();
        String updateProcessingText = reportDTO.getProcessingText();
        System.out.println("들어온 recordCode : " + updateReportCode);
        System.out.println("들어온 incProcessingText : " +updateProcessingText);

        reportService.updateReport(reportDTO.getReportCode(), updateProcessingText);


        //저장이 잘됐으면 저장 확인 얼럿을 띄워준다

        //서브밋이 결과에 따라 알럿창을 띄워주는 기능을 개발해야함.

        rttr.addFlashAttribute("insertRecord");
        return"redirect:/manager/report/list";


    }


    @GetMapping ("/manager/admin/list")
    public String viewAllAdmList (Model model) {
        List<ReportDTO> reports = reportService.viewAllAdmList() ;
        model.addAttribute("reports", reports);


        return "/manager/admin/list";
    }


    @GetMapping("/manager/admin/detail")
    public String viewOneAdmReport ( Integer reportCode, Model model) {
        ReportDTO admReport = reportService.findOneReportCord(reportCode);
        model.addAttribute("report", admReport);
        return "/manager/admin/detail";
    }


    @GetMapping ("/manager/member/list")
    public String checkAllMember (Model model) {
        List<MemberDTO> members = reportService.checkAllMember();
        model.addAttribute("members", members);

        return "manager/member/list";
    }

    @GetMapping("/manager/member/detail")
    public String checkOneMember (MemberDTO memberDTO, Model model) {
        MemberDTO oneMember = reportService.checkOneMember(memberDTO);
        model.addAttribute("oneMember", oneMember);
        return "manager/member/detail";
    }




//    @PostMapping("/report/list")
//    public ResponseEntity<String> checkDuplication (@RequestBody ReportDTO report) {
//        log.info("Request Check Id : { } ", report.getReportCode());
//        String result = ""
    // 자바단에서 만들어야 알럿을 이걸로 만들 수 있지 않을까..? 생각해보기
//    }


//    @Test //신고내역 가짜 데이터 생성
//    public void testJpa() {
//        for(int i=1; i<150; i++) {
//            this.reportService.create(String.format("%d번째 제목입니다", i), String.format("%d번째 내용입니다."), i);
//
//        }
//
//
//    }





//    @GetMapping("/memberStop")
//    public String memberStop(ReportDTO reportDTO, RedirectAttributes rttr) {
//        System.out.println("memberStop 호출함");
//
//        reportService.memberStop(reportDTO.getMemberId());
//        //경고횟수
//
//        return "redrirect:/manager/report/list";
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
//    @GetMapping("/manager/admin/list")
//    public String showAdminList (ReportDTO reportDTO, Model model) {
//        ReportDTO admin = reportService.showAdminList(reportDTO);
//        model.addAttribute("showAllAdmlist", admin);
//
//        return "/manager/admin/list";
//    }
}