package com._3dhs.tnproject.manager.dao;

import com._3dhs.tnproject.common.paging.SelectCriteria;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {
    List<ReportDTO> viewAllReport();

    ReportDTO viewOneReport(Integer reportCode);


    ReportDTO updateReport(int reportCode, String processingText);

    List<ReportDTO> viewAllAdmList();

    ReportDTO viewOneAdmReport(Integer reportCode);


    List<MemberDTO> checkAllMember();

    MemberDTO checkOneMember(MemberDTO memberDTO);

    int selectTotalCount(Map<String, String> searchMap);

    List<ReportDTO> selectBoardList(SelectCriteria selectCriteria);
}
