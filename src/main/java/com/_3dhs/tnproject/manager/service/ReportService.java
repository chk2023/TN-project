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
        log.info("boardList totalCount : {}", totalCount );

        //2. 페이징 처리와 연관된 값을 계산하여 SelectCriteria 타입의 객체에 담는다.
        int limit = 10; // 한 페이지에 보여줄 게시물의 수
        int buttonAmount = 10 ; //한 번에 보여질 페이징 버튼 수(하단에 노출시킬 페이징 버튼 수)

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount,searchMap);
        log.info("boardList selectCriteria : {}", selectCriteria);

        // 3. 요청 페이지와 검색 기준에 맞는 게시글을 조회해온다.
        List<ReportDTO> reportList = reportMapper.selectReportList(selectCriteria);
        log.info("boardList : {}", reportList );

        Map<String, Object> boardListAndPaging = new HashMap<>();
        boardListAndPaging.put("paging", selectCriteria);
        boardListAndPaging.put("reportList", reportList);

        return boardListAndPaging;

    }


    public ReportDTO selectReportDetail(Integer reportCode) {
        return reportMapper.selectReportDetail(reportCode);
    }


    public ReportDTO updateReport(int reportCode, String processingText) {
        return reportMapper.updateReport(reportCode, processingText);
    }


    public void memberStop(String subMemberId) {

        /*if()
         * 경고 횟수가 5회 이상이면 멤버디비에 접근해서 권한 수정(N-Y)
         * 경고 횟수가 5회 미만이면 알럿창 띄워서 "해당 처리를 할 수 없습니다" */
    }


    public List<ReportDTO> viewAllAdmList() {
        return reportMapper.viewAllAdmList();
    }

    public ReportDTO findOneReportCord(Integer reportCode) {
        return reportMapper.viewOneAdmReport(reportCode);
    }


    public List<MemberDTO> checkAllMember() {
        return reportMapper.checkAllMember();
    }


    public MemberDTO checkOneMember(MemberDTO memberDTO) {
        return reportMapper.checkOneMember(memberDTO);
    }


}



