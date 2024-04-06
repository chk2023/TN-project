package com._3dhs.tnproject.purchase.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.payment.service.PaymentService;
import com._3dhs.tnproject.post.dto.PostDTO;
import com._3dhs.tnproject.post.service.PostService;
import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import com._3dhs.tnproject.purchase.service.PurchaseService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

public class PurchaseController {

    private final PostService postService;
    private final PurchaseService purchaseService;
    private final MemberService memberService;

    public PurchaseController(PostService postService, PurchaseService purchaseService, MemberService memberService) {
        this.postService = postService;
        this.purchaseService = purchaseService;
        this.memberService = memberService;
    }

    @GetMapping("/post/{postCode}")
    public String getPostInfo(@PathVariable("postCode") Integer postCode, Model model, Authentication authentication) {
        MemberDTO currentMember = (MemberDTO) authentication.getPrincipal();

        PostDTO postDTO = postService.getPostByPostCode(postCode);

        boolean isPostPurchased = purchaseService.isPostPurchased(currentMember.getMemberCode(), postCode);

        if (isPostPurchased) {
            model.addAttribute("postInfo", postDTO);
            return "post_info";
        } else {
            return "purchase_fail";
        }

    }

    @PostMapping("/purchase_success")
    public String purchaseSuccess(@RequestParam String buyer_name,
                                  @RequestParam int tissuePrice,
                                  @RequestParam int postCode,
                                  Authentication authentication) {

        MemberDTO currentMember = (MemberDTO) authentication.getPrincipal();
        currentMember.setHaveTissue(currentMember.getHaveTissue() + tissuePrice);
        memberService.updateHaveTissue(currentMember);

        PurchaseDTO purchaseDTO = new PurchaseDTO(
                "USE",
                LocalDateTime.now(),
                tissuePrice,
                currentMember.getMemberCode(),
                postCode
        );

        return "/purchase_success";
    }




}
