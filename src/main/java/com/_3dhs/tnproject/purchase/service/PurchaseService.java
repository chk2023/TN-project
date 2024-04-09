package com._3dhs.tnproject.purchase.service;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.purchase.dao.PurchaseMapper;
import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PurchaseService {

    private final PurchaseMapper purchaseMapper;

    @Autowired
    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    public boolean isPostPurchased(int memberCode, int postCode) {

        int purchaseCount = purchaseMapper.purchaseCount(memberCode, postCode);

        return purchaseCount > 0;
    }

    public PurchaseDTO getPaidPostInfo(MemberDTO currentMember, Integer postCode) {

        int memberCode = currentMember.getMemberCode();

        boolean isPostPurchased = isPostPurchased(memberCode, postCode);

        if (!isPostPurchased) {
            int purchaseCount = purchaseMapper.purchaseCount(memberCode, postCode);
            if (purchaseCount > 0) {
                int postPrice = purchaseMapper.getPostPrice(postCode);
                return new PurchaseDTO("USE", LocalDateTime.now(), postPrice, memberCode, postCode);
            }

        }
        return null;
    }


    public void savePurchaseList(PurchaseDTO purchaseDTO) {

        purchaseMapper.insertPurchase(purchaseDTO);

    }
}
