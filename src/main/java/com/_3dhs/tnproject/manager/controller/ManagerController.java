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
    private final MemberService memberService;
    private final MessageSourceAccessor messageSourceAccessor;


    @GetMapping("/manager/report/list")
    public String viewAllReportList(Model model) {

        List<ReportDTO> reportList = reportService.viewAllReport();
        model.addAttribute("reportList", reportList);

        return "manager/report/list";


    }


    @GetMapping("/detail")
    public String viewOneReport(Model model, Integer reportCode) {
        ReportDTO report = reportService.viewOneReport(reportCode);
        model.addAttribute("detail", report);


        return "/manager/report/detail";
    }


    @GetMapping("/detail")
    public String updateReport(ReportDTO reportDTO, RedirectAttributes rttr) {

        // 신고 목록 상세에서 내역을 입력해서 완료 버튼을 누르면 해당 내용이 디비에 저장하는 기능

        Integer incomingRecordcode = reportDTO.getReportCode();
        String incProcessingText = reportDTO.getProcessingText();
        System.out.println("들어온 recordCode : " + incomingRecordcode);
        System.out.println("들어온 incProcessingText : " +incProcessingText);

        reportService.updateReport(reportDTO.getReportCode(), incProcessingText);


        //저장이 잘됐으면 저장 확인 얼럿을 띄워준다

        //서브밋이 결과에 따라 알럿창을 띄워주는 기능을 개발해야함.

        rttr.addFlashAttribute("insertRecord");
        return"redirect:/manager/report/list";


    }

    @GetMapping("/memberStop")
    public String memberStop(ReportDTO reportDTO, RedirectAttributes rttr) {
        System.out.println("memberStop 호출함");

        reportService.memberStop(reportDTO.getSubMemberId());
        //경고횟수

        return "redrirect:/manager/report/list";
    }

    @GetMapping("/memActivate")
    public String memberActivate(ReportDTO reportDTO,RedirectAttributes rttr) {

        return "redirect:/manager/report/list";
    }



//    @GetMapping("/manager/admin/list")
//    public String showAdminList (ReportDTO reportDTO) {
//        ReportDTO admin = reportService.showAdminList(reportDTO);
//                return "/manager/admin/adminList";
//    }
}