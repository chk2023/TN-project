package com._3dhs.tnproject.purchase.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PurchaseDTO {

    private int tissueCode;
    private String impUid;
    private String merchantUid;
    private String orderClass;
    private LocalDateTime orderDate;
    private int tissuePrice;
    private int tMemberCode;
    private Integer postCode;

    public PurchaseDTO(String orderClass, LocalDateTime orderDate, int tissuePrice, int tMemberCode, Integer postCode) {
        this.orderClass = orderClass;
        this.orderDate = orderDate;
        this.tissuePrice = tissuePrice;
        this.tMemberCode = tMemberCode;
        this.postCode = postCode;
    }
}
