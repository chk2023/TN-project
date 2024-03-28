package com._3dhs.tnproject.purchase.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TissueDTO {
    private int orderCode;
    private int orderNum;
    private String orderClass; //TODO : enum으로 대체할지 작업자가 설정하기
    private LocalDateTime orderDate;
    private int tissuePrice;
    private int tMemberCode;
    private Integer postCode;

    public TissueDTO(int orderNum, String orderClass, LocalDateTime orderDate, int tissuePrice, int tMemberCode) {
        this.orderNum = orderNum;
        this.orderClass = orderClass;
        this.orderDate = orderDate;
        this.tissuePrice = tissuePrice;
        this.tMemberCode = tMemberCode;
    }
}
