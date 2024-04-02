package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.manager.service.ReportService;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
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
        System.out.println(reportList.size());
        model.addAttribute("reportList", reportList);

        return "manager/report/list";


    }


    @GetMapping("/report/detail")
    public String viewOneReport(Model model1, Integer reportCode) {
        ReportDTO report = reportService.viewOneReport(reportCode);
        model1.addAttribute("detail", report);


        return "manager/report/detail";
    }


    @PostMapping("/report/detail") //getMapping으로 값을 넘길 이유가 없으니까, 포스트 매핑을 시켜도 될 것 같은데..
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
    public String viewAllAdmList (ReportDTO reportDTO, Model model) {
        List<ReportDTO> reports = reportService.viewAllAdmList(reportDTO) ;
        model.addAttribute("viewAllAdmList", reports);




        return "/manager/admin/list";
    }


    @GetMapping("/manager/admin/detail")
    public String viewOneAdmReport ( Integer reportCode, Model model) {
        ReportDTO admReport = reportService.findOneReportCord(reportCode);
        model.addAttribute("report", admReport);
        return "/manager/admin/detail";
    }


    @GetMapping ("/manager/member/list")
    public String checkAllMember (MemberDTO memberDTO, Model model) {
        List<MemberDTO> members = reportService.checkAllMember(memberDTO);
        model.addAttribute("members", members);


        return "manager/member/list";
    }    //리턴이 파싱처리가 안되서 제대로 안되고 잇다.. 어디서 막힌걸까
        //파싱개.....나리민들레 진또배기 으컁컁






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