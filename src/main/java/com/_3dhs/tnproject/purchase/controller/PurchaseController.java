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
    public String getPostInfo(@RequestBody PostDTO postDTO, @AuthenticationPrincipal MemberDTO memberDTO) {
        int memberCode = memberDTO.getMemberCode();

//        PostDTO postDTO = postService.getPostByPostCode(postCode);

//        boolean isPostPurchased = purchaseService.isPostPurchased(currentMember.getMemberCode(), postCode);
        PurchaseDTO purchased = purchaseService.getPaidPostInfo(memberCode, postDTO.getPostCode());

        if (purchased != null) {

        } else {

        }

        return null;
    }

    // 서비스로 이동
    @PostMapping("/purchaseSuccess")
    public String purchaseSuccess(@RequestParam int postCode, Authentication authentication) {

        MemberDTO currentMember = (MemberDTO) authentication.getPrincipal();
        int memberCode = currentMember.getMemberCode();
        int tissuePrice = purchaseService.getPostPrice(postCode);

        int ntissuePrice = currentMember.getHaveTissue() - tissuePrice;
        memberService.updateHaveTissue(currentMember);

        PurchaseDTO purchaseDTO = new PurchaseDTO(
                "USE",
                LocalDateTime.now(),
                tissuePrice,
                currentMember.getMemberCode(),
                postCode
        );
        purchaseService.savePurchaseList(purchaseDTO);

        return "/purchase_success";
    }




}
