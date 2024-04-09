package com._3dhs.tnproject.manager.dto;

import com._3dhs.tnproject.manager.model.ReportContents;
import com._3dhs.tnproject.manager.model.ReportState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@Service
public class ReportDTO {
    private int reportCode;
    private int rCategoryCode;
    private String rCategoryName;
    private String reportText;
    private LocalDateTime reportDate;
    private String formattingReportDate;
    private ReportState reportStatus;
    private LocalDateTime processingDate;
    private String processingText;
    private int subMemberCode;
    private String memberId;
    private int managerCode;
    private int reporterCode;
    private String reporterId;
    private ReportContents reportContent;
    private Integer postCode;
    private Integer cmtCode;

    public String  formattingDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }

    public void makeFormattingReportDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.formattingReportDate = reportDate.format(formatter);
    }
}
