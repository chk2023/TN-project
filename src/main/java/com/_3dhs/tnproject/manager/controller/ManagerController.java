package com._3dhs.tnproject.manager.controller;

import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.manager.service.ReportService;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final ReportService reportService;
    private final MemberService memberService;
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

    @GetMapping("/manager/reportList")
    public String viewAllReportList(Model model) {

        List<ReportDTO> reportList = reportService.viewAllReport();
        model.addAttribute("reportList", reportList);

        return "/manager/reportList";


    }

    /*신고버튼 누름 -> 신고 창 띄워주는 컨트롤러 호출
     작성한 후에 등록 버튼 클릭
     -> update를 해서 신고내용을 디비에 업데이트
     html에서 submit사용해서 그 내용을 컨트롤러로 보내서 update함

     if  신고횟수가 5이면 게시물정보를 가져와서
     게시물 아이디를 정지 or 삭제 처리

//    @GetMapping("admin")
//    public String adminP() {
//        return "admin";
//    }
     1. 일단 신고버튼 누르고 페이지 띄우는것
     2. 신고내용 적고 서브밋했을때
      db에 신고내용이랑 신고카운트가 저장이 잘되는지 확인*/

}
