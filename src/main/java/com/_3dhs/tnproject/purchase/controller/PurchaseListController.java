package com._3dhs.tnproject.purchase.controller;


import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.purchase.dto.PurchaseDTO;
import com._3dhs.tnproject.purchase.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/purchase")
public class PurchaseListController {

    private final PurchaseService purchaseService;

    public PurchaseListController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/list")
    public String getPurchaseListPage () {

        return "purchase/list";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<String> getPurchaseData(@AuthenticationPrincipal MemberDTO memberDTO) {

        List<String> purchaseList = purchaseService.getPurchaseAndPaymentList(memberDTO.getMemberCode());
//        model.addAttribute("purchaseList", purchaseList);

        return purchaseList;
    }




}
