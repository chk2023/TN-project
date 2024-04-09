package com._3dhs.tnproject.purchase.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.payment.service.PaymentService;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import com._3dhs.tnproject.purchase.dao.PurchaseMapper;
import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import com._3dhs.tnproject.purchase.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
//    public String getPostInfo(@RequestParam("postCode") Integer postCode, Model model, @AuthenticationPrincipal MemberDTO memberDTO) {
    public String getPostInfo(@RequestBody Map<String, Integer> requestBody, @AuthenticationPrincipal MemberDTO memberDTO) {
        int postCode = requestBody.get("postCode");
        int memberCode = memberDTO.getMemberCode();

//        PostDTO postDTO = postService.getPostByPostCode(postCode);

//        boolean isPostPurchased = purchaseService.isPostPurchased(currentMember.getMemberCode(), postCode);
        PurchaseDTO purchased = purchaseService.getPaidPostInfo(memberCode, postCode);

        if (purchased != null) {
//            model.addAttribute("postInfo", purchased);
            return "{\"status\": \"success\", \"message\": \"post_info\"}";
        } else {
            return "{\"status\": \"fail\", \"message\": \"purchase_fail\"}";
        }

    }

    @PostMapping("/purchaseSuccess")
    public String purchaseSuccess(@RequestParam String buyer_name,
                                  @RequestParam int tissuePrice,
                                  @RequestParam int postCode,
                                  Authentication authentication) {

        MemberDTO currentMember = (MemberDTO) authentication.getPrincipal();
        int memberCode = currentMember.getMemberCode();

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
