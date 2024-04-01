package com._3dhs.tnproject.manager.service;

import com._3dhs.tnproject.manager.dao.ReportMapper;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;


    public List<ReportDTO> viewAllReport() {
        return reportMapper.viewAllReport();
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

    public ReportDTO showAdminList(ReportDTO reportDTO) {
        return reportMapper.showAdminList(reportDTO) ;

    }
}



