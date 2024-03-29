package com._3dhs.tnproject.manager.dto;

import com._3dhs.tnproject.manager.model.ReportContents;
import com._3dhs.tnproject.manager.model.ReportState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReportDTO {
    private int reportCode;
    private int rCategoryCode;
    private String rCategoryName;
    private String reportText;
    private LocalDateTime reportDate;
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
}
