package com._3dhs.tnproject.purchase.service;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.payment.dao.PaymentMapper;
import com._3dhs.tnproject.purchase.dao.PurchaseMapper;
import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final PaymentMapper paymentMapper;

    public PurchaseService(PurchaseMapper purchaseMapper, PaymentMapper paymentMapper) {
        this.purchaseMapper = purchaseMapper;
        this.paymentMapper = paymentMapper;
    }

    public boolean isPostPurchased(int memberCode, int postCode) {

        int purchaseCount = purchaseMapper.purchaseCount(memberCode, postCode);

        return purchaseCount > 0;
    }


    public PurchaseDTO getPaidPostInfo(MemberDTO memberDTO, int postCode) {

        // 구매 여부 확인
        boolean isPostPurchased = isPostPurchased(memberDTO.getMemberCode(), postCode);

        int postPrice = purchaseMapper.getPostPrice(postCode);
        int currentTissue = memberDTO.getHaveTissue();

        if (currentTissue - postPrice < 0) {
            throw new RuntimeException("보유한 티슈가 부족합니다.");
        } else {
            if (isPostPurchased) {
                throw new RuntimeException("이미 구매한 글");
            } else {
                PurchaseDTO purchaseDTO = new PurchaseDTO(
                        "USE",
                        LocalDateTime.now(),
                        postPrice,
                        memberDTO.getMemberCode(),
                        postCode
                );
                savePurchaseList(purchaseDTO);
                return purchaseDTO;
            }
        }



        // 보유 티슈가 충분한지 확인
        // 충분하지 않으면 exception

        // 구매 처리
    }


    public int getPostPrice(int postCode) {
        return purchaseMapper.getPostPrice(postCode);
    }


    public void savePurchaseList(PurchaseDTO purchaseDTO) {

        purchaseMapper.insertPurchase(purchaseDTO);

    }


    public List<String> getPurchaseAndPaymentList(int memberCode) {

        List<String> alldata = new ArrayList<>();

        List<String> purchaseList = purchaseMapper.selectPurchaseList(memberCode);
        alldata.addAll(purchaseList);

        List<String> paymentList = paymentMapper.selectPaymentList(memberCode);
        alldata.addAll(paymentList);

        return alldata;
    }
}
