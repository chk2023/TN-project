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
        return  reportMapper.viewAllReport();
    }

    public ReportDTO viewOneReport(Integer reportCode) {

        return reportMapper.viewOneReport(reportCode);
    }

    public String processingReport(String proccessingText) {

        return reportMapper.processingReport(proccessingText);
    }
}
