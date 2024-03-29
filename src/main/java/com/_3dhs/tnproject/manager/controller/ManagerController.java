package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.manager.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
public  class ManagerController {

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
        model.addAttribute("report", report);


        return"/manager/report/detail";
}

    @GetMapping("/detail")
    public String updateReport(ReportDTO reportDTO, RedirectAttributes rttr) {

        // 신고 목록 상세에서 내역을 입력해서 완료 버튼을 누르면 해당 내용이 디비에 저장하는 기능

        //얘에느는........ 신고 내역을 처리하는 메소드인거고...
        //조인을 해서 멤버 코드를 가져오는건 신고목록 상세조회....
        //그러면 여기서는 메머 아이디를"처리"해야 하는건 아니고 상세 조회 페이지에서 보여지게 하는 것.

        ReportDTO record = reportService.updateReport(reportDTO.getReportCode(), reportDTO.getProcessingText());
        record.setProcessingText(reportDTO.getProcessingText());
        reportService.updateReport(reportDTO.getReportCode(), reportDTO.getProcessingText());

        //record를 db에 저장한 후에??????????


        //저장이 잘됐으면 저장 확인 얼럿을 띄워준다


        //서브밋이 결과에 따라 알럿창을 띄워주는 기능을 개발해야함.

        rttr.addFlashAttribute("insertRecord");
        return"redirect:/manager/report/list";


    }









}





