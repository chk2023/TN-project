package com._3dhs.tnproject.purchase.controller;


import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import com._3dhs.tnproject.purchase.service.PurchaseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PurchaseListController {

    private final PurchaseService purchaseService;

    public PurchaseListController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    @GetMapping("/purchase/list")
    public String getPurchaseListPage (Model model, @AuthenticationPrincipal MemberDTO memberDTO) {

//        List<String> purchaseList = purchaseService.getPurchaseList(memberDTO.getMemberCode());
//        model.addAttribute("purchaseList", purchaseList);

        return "/purchase/list";
    }


}
