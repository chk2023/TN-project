package com._3dhs.tnproject.purchase.dao;

import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    int purchaseCount(int memberCode, int postCode);

    int getPostPrice(Integer postCode);



    void insertPurchase(PurchaseDTO purchaseDTO);

    List<String> selectPurchaseList(int memberCode);
}
