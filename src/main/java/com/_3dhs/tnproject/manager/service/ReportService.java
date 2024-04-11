package com._3dhs.tnproject.manager.service;

import com._3dhs.tnproject.common.paging.Pagenation;
import com._3dhs.tnproject.common.paging.SelectCriteria;
import com._3dhs.tnproject.manager.dao.ReportMapper;
import com._3dhs.tnproject.manager.dto.ReportDTO;
import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service @Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;


    public Map<String, Object> selectReportList(Map<String, String> searchMap, int page) {

        // 1. 전체 게시글 수 확인 ( 검색어가 있는 경우 포함) => 페이징 처리를 위해
        int totalCount = reportMapper.selectTotalCount(searchMap);


        //2. 페이징 처리와 연관된 값을 계산하여 SelectCriteria 타입의 객체에 담는다.
        int limit = 10; // 한 페이지에 보여줄 게시물의 수
        int buttonAmount = 10 ; //한 번에 보여질 페이징 버튼 수(하단에 노출시킬 페이징 버튼 수)

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount,searchMap);


        // 3. 요청 페이지와 검색 기준에 맞는 게시글을 조회해온다.
        List<ReportDTO> reportList = reportMapper.selectReportList(selectCriteria);


        Map<String, Object> boardListAndPaging = new HashMap<>();
        boardListAndPaging.put("paging", selectCriteria);
        boardListAndPaging.put("reportList", reportList);




        return boardListAndPaging;

    }


    public ReportDTO selectReportDetail(Integer reportCode) {
        return reportMapper.selectReportDetail(reportCode);
    }


    public void updateReport(ReportDTO reportDTO) {
        reportMapper.updateReport(reportDTO);
    }


    public void memberStop(String memberId) {

        reportMapper.memberStop(memberId);
    }



    public Map<String, Object> selectAdminList(Map<String, String> searchMap, int page) {

        int totalCount = reportMapper.selectTotalCount(searchMap);

        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount,searchMap);
        List<ReportDTO> adminList = reportMapper.selectAdminList(selectCriteria);

        Map<String, Object> boardListAndPaging = new HashMap<>();
        boardListAndPaging.put("paging", selectCriteria);
        boardListAndPaging.put("adminList", adminList);

        return boardListAndPaging;

    }

    public ReportDTO selectAdminDetail(Integer reportCode) {
        return reportMapper.selectAdminDetail(reportCode);

    }

    public Map<String, Object> selectMemberList(Map<String, String> searchMap, int page) {

        int totalCount = reportMapper.selectTotalCount(searchMap);

        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount,searchMap);
        List<ReportDTO> memberList = reportMapper.selectMemberList(selectCriteria);

        Map<String, Object> boardListAndPaging = new HashMap<>();
        boardListAndPaging.put("paging", selectCriteria);
        boardListAndPaging.put("memberList", memberList);

        return boardListAndPaging;
    }

    public MemberDTO selectOneMember(MemberDTO memberDTO) {
        return reportMapper.selectOneMember(memberDTO);

    }
}



