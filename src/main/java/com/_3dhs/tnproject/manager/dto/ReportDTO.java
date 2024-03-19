package com._3dhs.tnproject.manager.dto;

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
    private String reportText;
    private LocalDateTime reportDate;
    private String reportStatus; //TODO : enum 사용할지 작업자 재량
    private LocalDateTime processingDate;
    private String processingText;
    private int subMemberCode;
    private int managerCode;
    private int reporterCode;
    private String reportContent;
    private Integer postCode;
    private Integer cmtCode;
}
