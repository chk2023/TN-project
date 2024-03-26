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

import java.sql.*;
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


        return"/manager/report/detail";
}





@GetMapping("/detail")
public String insertRecord(Model model, Integer reportCode, RedirectAttributes rttr) {

    // 신고 목록 상세에서 내역을 입력해서 완료 버튼을 누르면 해당 내용이 디비에 저장하는 기능

    ReportDTO record = reportService.insertRecord(reportCode);
    model.addAttribute("insertRecord", record);
   // model.getAttribute("insertRecord", reportCode);
    //모델 객체에 reportCode 값을 담는다.
    System.out.println(record);

    //db에서 reportcode로 담은 값을 페이지에 출력...을....
    int repNum = 0;

    repNum = record.getReportCode();




//    // JDBC 드라이버 로드
//    Class.forName("com.mysql.jdbc.Driver");
//
//    // 데이터베이스 연결
//    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name", "username", "password");
//
//    // 쿼리 실행
//    Statement statement = connection.createStatement();
//    ResultSet resultSet = statement.executeQuery("SELECT * FROM table_name");
//
//    // 결과 처리 및 출력
//    while (resultSet.next()) {
//        String value = resultSet.getString("column_name");
//        System.out.println("Value: " + value);
//        // 여기서는 콘솔에 출력했지만, 실제로는 모델에 데이터를 추가하여 페이지에 전달합니다.
//    }
//
//    // 연결 종료
//    resultSet.close();
//    statement.close();
//    connection.close();
//} catch (Exception e) {
//        e.printStackTrace();
//    }




    // 페이지가 로딩될 때 값이 들어와야 한다.
    for(int i = 0 ; i > 0 ; i ++) {
        //순환하면서 확인해줘. 라는 명령문을 쓸 때에 포문을 사용했던 것 같은데..

    }







    //

    //오호.....???? 윗 코드가 잘못된건가 에에?



    //훌라훌라훌라~~~~ 훌라훌라후~~울라



    //변경된 값을 객체에 담아 디비에 보내준다..?

    //

    rttr.addFlashAttribute("insertRecord");


    return "redirect:/manager/report/list";

}

//    @GetMapping("/")
//    public String failedInsert(Model model, Integer reportCode, RedirectAttributes rttr) {
//
//        // 신고 목록 상세에서 내역을 입력해서 디비에 저장하는 기능
//        //insetRC 페이지랑 어떻게 연동을 해야 하는가..
//        //detail 페이지랑 연동해서, detail 페이지에서 버튼 기능 추가하면 되는거 아닌가...
//        //디비에 저장되는 기능이랑, 버튼을 눌렀을 때 알럿창이 뜨는 거는 기능?????을 분리해야 되지 않나. => 기능이라고 묶는 것도 좀..
//
//        //객체를 디비에 보내서 값을 입력하고, 완료 했을 경우 알럿 창으로 띄우게 만드는 기능.
//
//        ReportDTO record = reportService.insertRecord(reportCode);
//        model.addAttribute("insertRecord", record);
//
//        //변경된 값을 객체에 담아 디비에 보내준다..?
//
//        return "redirect:/manager/report/list";
//    }

}


//    @GetMapping("admin")
//    public String adminP() {
//        return "admin";
//    }
//
//}
