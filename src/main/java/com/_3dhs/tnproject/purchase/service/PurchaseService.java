package com._3dhs.tnproject.purchase.service;

import com._3dhs.tnproject.purchase.dao.PurchaseMapper;

public class PurchaseService {

    private final PurchaseMapper purchaseMapper;

    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    public boolean isPostPurchased(int memberCode, int postCode) {

        int purchaseCount = purchaseMapper.purchaseCount(memberCode, postCode);

        return purchaseCount > 0;
    }
}
