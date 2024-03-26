package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.manager.service.ReportService;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final ReportService reportService;
    private final MessageSourceAccessor messageSourceAccessor;


    //회원 로그인 페이지가 만들어지면 그쪽으로 경로 변경해주기
    @GetMapping("/manager/m-log")
    public void loginMag() {

    }

    //로그인 오류시 나올 페이지랑 연동시키긔.... 경로 경로 경로-_-
    @GetMapping("/manager/loginfail")
    public String loginFailed(RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("error.login"));
        return "redirect:/manager/manage-log";
    }

    @GetMapping("/manager/m-timeline")
    public void MagTimeline() {

    }

    @GetMapping("/manager/report/list")
    public void viewAllReportList(Model model) {

        List<ReportDTO> reportList = reportService.viewAllReport();
        model.addAttribute("reportList", reportList);

    }

    @GetMapping("/manager/report")
    public String viewOneReport(Model model, Integer reportCode) {
        ReportDTO report = reportService.viewOneReport(reportCode);
        model.addAttribute("viewOneReport", report);

        return "/manager/report/detail";
    }

//    @GetMapping("/admin")
//    public String processingReport(Model model, String proccessingText) {
//        //신고목록 상세조회에서 내용을 입력하고 완료 버튼을 눌렀을 때의 동작
//
//        //입력 받은 스트링 값을 담을 객체를 선언하고
//        //훌라훌라훌라 훌~라 훌~라 훌라~~~ 훌라춤을 추우는 탬버륀~~~~
//        String reportProcessingText = reportService.processingReport(proccessingText);
//        //  model.addAttribute("processingReport", processingReport);
//
//
//        //완료 버튼을 누르면 디비에 해당 값이 저장된다.
//
//        //mapper에서 insert로 처리해야 할 듯....
//
//
//        return "/manager/report/processing ";
//    }


    @GetMapping("/insertRC")
    public String inserRecord(Model model, Integer reportCode, RedirectAttributes rttr) {

        // 신고 목록 상세에서 내역을 입력해서 디비에 저장하는 기능
        //insetRC 페이지랑 어떻게 연동을 해야 하는가..
        ReportDTO record = reportService.insertRecord(reportCode);
        model.addAttribute("insertRecord", record);

        //변경된 값을 객체에 담아 디비에 보내준다..?





         return "/manager/report/list";

    }
}


//    @GetMapping("admin")
//    public String adminP() {
//        return "admin";
//    }
//
//}
