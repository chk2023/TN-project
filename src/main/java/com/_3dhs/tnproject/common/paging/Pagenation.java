package com._3dhs.tnproject.common.paging;

import java.util.HashMap;
import java.util.Map;

public class Pagenation {
    public static SelectCriteria getSelectCriteria(int page, int totalCount, int limit, int buttonAmount, Map<String, String> searchMap) {
        // 총 페이지 수 계산
        int maxPage = (int) Math.ceil((double) totalCount / limit);

        // 페이징바 시작 숫자
        int startPage = (int) (Math.ceil((double) page / buttonAmount) - 1) * buttonAmount + 1;

        // 페이징바 끝 숫자
        int endPage = startPage + buttonAmount - 1;

        // max 페이지가 end 페이지보다 더 작은 경우 end 페이지는 max 페이지이다.
        if (maxPage < endPage) endPage = maxPage;

        // 마지막 페이지는 0이 될 수 없으므로 게시물이 아무것도 존재하지 않으면 max, end는 0이 된다.
        if (maxPage == 0 && endPage == 0) {
            maxPage = startPage;
            endPage = startPage;
        }

        //offset 계산
        int offset = (page - 1) * limit;

        return new SelectCriteria(page, totalCount, limit, buttonAmount, maxPage, startPage, endPage,
                offset, searchMap.get("searchCondition"), searchMap.get("searchValue"));

    }

    public static SelectCriteria getSelectCriteria(int page, int totalCount, int limit, int buttonAmount) {
        return getSelectCriteria(page, totalCount, limit, buttonAmount, new HashMap<>());

    }

    // 검색 기능이 필요 없을 때 사용하는 메소드 오버로드 페이징만 할 때 사용
    public static SelectCriteria getSelectCriteriaWithoutSearch(int page, int totalCount, int limit, int buttonAmount) {
        return getSelectCriteria(page, totalCount, limit, buttonAmount, new HashMap<>());
    }
}
