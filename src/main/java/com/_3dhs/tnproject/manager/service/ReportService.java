package com._3dhs.tnproject.manager.service;

import com._3dhs.tnproject.manager.dao.ReportMapper;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;


    public List<ReportDTO> viewAllReport() {
        List<ReportDTO> list = reportMapper.viewAllReport();
        for (ReportDTO reportDTO : list) {
            reportDTO.makeFormattingReportDate();
        }
        return list;
    }

    public ReportDTO viewOneReport(Integer reportCode) {

        return reportMapper.viewOneReport(reportCode);
    }


    public ReportDTO updateReport(int reportCode, String processingText) {
        return reportMapper.updateReport(reportCode, processingText);
    }


    public void memberStop(String subMemberId) {

        /*if()
         * 경고 횟수가 5회 이상이면 멤버디비에 접근해서 권한 수정(N-Y)
         * 경고 횟수가 5회 미만이면 알럿창 띄워서 "해당 처리를 할 수 없습니다" */
    }


    public List<ReportDTO> viewAllAdmList(ReportDTO reportDTO) {
        return reportMapper.viewAllAdmList(reportDTO);
    }

    public ReportDTO findOneReportCode(Integer reportCode) {
        return reportMapper.viewOneAdmReport(reportCode);
    }
}



