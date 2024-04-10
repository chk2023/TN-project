package com._3dhs.tnproject.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor
public class SelectCriteria {

    private int page;
    private int totalCount;
    private int limit;
    private int buttonAmount;
    private int maxPage;
    private int startPage;
    private int endPage;
    private int offset;
    private String searchCondition;
    private String searchValue;

}
