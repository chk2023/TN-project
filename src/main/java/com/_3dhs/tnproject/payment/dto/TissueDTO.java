package com._3dhs.tnproject.payment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TissueDTO {

    private int tissueCode;
    private String impUid;
    private String merchantUid;
    private String orderClass;
    private LocalDateTime orderDate;
    private int tissuePrice;
    private int tMemberCode;
    private Integer postCode;

    public TissueDTO(String impUid, String merchantUid, String orderClass, LocalDateTime orderDate, int tissuePrice, int tMemberCode) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.orderClass = orderClass;
        this.orderDate = orderDate;
        this.tissuePrice = tissuePrice;
        this.tMemberCode = tMemberCode;
    }
}
