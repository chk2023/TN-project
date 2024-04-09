package com._3dhs.tnproject.manager.dao;

import com._3dhs.tnproject.common.paging.SelectCriteria;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    List<ReportDTO> selectReportList(SelectCriteria selectCriteria);

    ReportDTO selectReportDetail(Integer reportCode);

    List<ReportDTO> selectAdminList(SelectCriteria selectCriteria);

    void updateReport(ReportDTO reportDTO);

    ReportDTO selectAdminDetail(Integer reportCode);

    List<ReportDTO> selectMemberList(SelectCriteria selectCriteria);

    MemberDTO selectOneMember(MemberDTO memberDTO);


    int selectTotalCount(Map<String, String> searchMap);


    void memberStop(MemberDTO memberDTO);
}
