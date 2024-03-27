package com._3dhs.tnproject.purchase.service;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.AuthService;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.purchase.dao.PaymentMapper;
import com._3dhs.tnproject.purchase.dto.TissueDTO;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private PaymentMapper paymentMapper;

    private  MemberService memberService;
    private AuthService authService;

    public void paymentProcess(TissueDTO tissueDTO) {

        TissueDTO tissue = new TissueDTO();

        tissue.setTMemberCode(tissueDTO.getTMemberCode());
        tissue.setTissuePrice(tissueDTO.getTissuePrice());

//        paymentMapper.insertPayment(tissue);

        String userId = authService.getCurrentUserId();
        MemberDTO member = memberService.getMemberbyId(userId);

        int cTissue = member.getHaveTissue();
        int nTissue = cTissue + tissueDTO.getTissuePrice();
        member.setHaveTissue(nTissue);
        memberService.updateHaveTissue(member.getMemberId(), nTissue);

        paymentMapper.insertPayment(tissueDTO);

    }

    public void paymentSuccess(TissueDTO tissueDTO) {

        String currentUserId = authService.getCurrentUserId();
        MemberDTO currentMember = memberService.getMemberbyId(currentUserId);
        int nTissuePrice = currentMember.getHaveTissue() + tissueDTO.getTissuePrice();
        currentMember.setHaveTissue(nTissuePrice);
        memberService.updateHaveTissue(currentMember.getMemberId(), nTissuePrice);

        TissueDTO nTissue = new TissueDTO();
        nTissue.setOrderClass("BUY");
        nTissue.setOrderDate(LocalDateTime.now());
        nTissue.setTissuePrice(tissueDTO.getTissuePrice());
        nTissue.setTMemberCode(currentMember.getMemberCode());
        nTissue.setPostCode(tissueDTO.getPostCode());
        paymentMapper.insertPayment(nTissue);

    }




}
