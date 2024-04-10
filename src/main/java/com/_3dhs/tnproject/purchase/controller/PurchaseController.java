package com._3dhs.tnproject.purchase.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.payment.service.PaymentService;
import com._3dhs.tnproject.post.dto.LikeListDTO;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import com._3dhs.tnproject.purchase.dao.PurchaseMapper;
import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import com._3dhs.tnproject.purchase.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PurchaseController {

    private final PostService postService;
    private final PurchaseService purchaseService;
    private final MemberService memberService;

    public PurchaseController(PostService postService, PurchaseService purchaseService, MemberService memberService) {
        this.postService = postService;
        this.purchaseService = purchaseService;
        this.memberService = memberService;
    }


    @PostMapping("/getPaidPostInfo")
    @ResponseBody
    public ResponseEntity<String> getPostInfo(@RequestBody PostDTO postDTO, @AuthenticationPrincipal MemberDTO memberDTO) {

        int memberCode = memberDTO.getMemberCode();

        // 결제를 요청하고 구매 정보를 가져옴
        PurchaseDTO purchaseDTO = purchaseService.getPaidPostInfo(memberDTO, postDTO.getPostCode());


        // 구매 정보가 null이 아니고 결제가 성공했을 때 티슈를 업데이트하고 DB에 저장
        if (purchaseDTO != null) {

            int postPrice = purchaseService.getPostPrice(postDTO.getPostCode());
            int ntissuePrice = memberDTO.getHaveTissue() - postPrice;

            if (ntissuePrice >= 0) {
                memberService.updateHaveTissue(memberDTO);
                return ResponseEntity.ok("결제가 완료되었습니다.");
            } else {
                return ResponseEntity.ok("티슈가 부족하여 결제가 취소됩니다.");
            }
        } else {
            return ResponseEntity.ok("구매 정보를 찾을 수 없습니다.");
        }

    }






}
