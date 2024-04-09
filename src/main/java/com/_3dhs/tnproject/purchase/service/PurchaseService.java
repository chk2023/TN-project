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

    public PurchaseDTO getPaidPostInfo(int memberCode, Integer postCode) {

        // 구매 여부 확인
        boolean isPostPurchased = isPostPurchased(memberCode, postCode);

        if (isPostPurchased) {
            throw new RuntimeException("이미 구매한 글");


//            int postPrice = purchaseMapper.getPostPrice(postCode);
//            return new PurchaseDTO("USE", LocalDateTime.now(), postPrice, memberCode, postCode);
        }

        // 보유 티슈가 충분한지 확인
        // 충분하지 않으면 exception

        // 구매 처리

//        else {
//            int purchaseCount = purchaseMapper.purchaseCount(memberCode, postCode);
//            if (purchaseCount > 0) {
//                int postPrice = purchaseMapper.getPostPrice(postCode);
//                return new PurchaseDTO("USE", LocalDateTime.now(), postPrice, memberCode, postCode);
//            }
//        }

        return null;
    }

    public int getPostPrice(int postCode) {
        return purchaseMapper.getPostPrice(postCode);
    }


    public void savePurchaseList(PurchaseDTO purchaseDTO) {

        purchaseMapper.insertPurchase(purchaseDTO);

    }


}
