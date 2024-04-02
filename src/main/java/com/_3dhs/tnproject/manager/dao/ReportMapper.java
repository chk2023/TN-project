package com._3dhs.tnproject.manager.dao;

import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<ReportDTO> viewAllReport();

    ReportDTO viewOneReport(Integer reportCode);


    ReportDTO updateReport(int reportCode, String processingText);

    List<ReportDTO> viewAllAdmList(ReportDTO reportDTO);

    ReportDTO viewOneAdmReport(ReportDTO reportDTO);

    List<MemberDTO> viewAllMember(MemberDTO memberDTO);
}
