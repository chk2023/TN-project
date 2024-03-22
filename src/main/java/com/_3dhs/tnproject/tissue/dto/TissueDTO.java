package com._3dhs.tnproject.tissue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TissueDTO {
    private int orderCode;
    private String orderClass; //TODO : enum으로 대체할지 작업자가 설정하기
    private LocalDateTime orderDate;
    private int tissuePrice;
    private int memberCode;
    private Integer postCode;
}
