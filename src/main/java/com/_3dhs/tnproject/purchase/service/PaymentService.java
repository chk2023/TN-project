package com._3dhs.tnproject.purchase.service;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.AuthService;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.purchase.dao.PaymentMapper;
import com._3dhs.tnproject.purchase.dto.TissueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }


    public void savePaymentList(TissueDTO tissueDTO) {

        paymentMapper.insertPayment(tissueDTO);
    }

//    private PaymentMapper paymentMapper;
//
//    private  MemberService memberService;
//
//    private AuthService authService;

//    public void paymentSuccess(TissueDTO tissueDTO) {
//
//        String currentUserId = authService.getCurrentUserId();
//        MemberDTO currentMember = memberService.getMemberbyId(currentUserId);
//        int nTissuePrice = currentMember.getHaveTissue() + tissueDTO.getTissuePrice();
//        currentMember.setHaveTissue(nTissuePrice);
//        memberService.updateHaveTissue(currentMember.getMemberId(), nTissuePrice);
//
//        TissueDTO nTissue = new TissueDTO();
//        nTissue.setOrderClass("BUY");
//        nTissue.setOrderDate(LocalDateTime.now());
//        nTissue.setTissuePrice(tissueDTO.getTissuePrice());
//        nTissue.setTMemberCode(currentMember.getMemberCode());
//        nTissue.setPostCode(tissueDTO.getPostCode());
//        paymentMapper.insertPayment(nTissue);
//
//    }




}
