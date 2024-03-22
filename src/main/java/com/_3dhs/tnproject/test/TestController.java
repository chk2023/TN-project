package com._3dhs.tnproject.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TestController {
   private final MessageSourceAccessor messageSourceAccessor;

    @GetMapping("/error/global")
    public String testErrorMessage(RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", messageSourceAccessor.getMessage("error.global"));
        return "/error/global";
    }

    @GetMapping("/layout/01_layout")
    public void goto01_layout() {
    }
    @GetMapping("/layout/02_layout")
    public void goto02_layout() {

    }
    @GetMapping("/layout/03_layout")
    public void goto03_layout() {
    }
    @GetMapping("/layout/04_layout")
    public void goto04_layout() {

    }
    @GetMapping("/layout/05_layout")
    public void goto05_layout() {
    }
    @GetMapping(value = {"/common/testhub"})
    public String gotoTestHubPage() {
        return "/common/testhub";
    }


    /* 결제 */
    @GetMapping("/member/purchase/purchase")
    public void gotoPurchase() {}

    @GetMapping("/member/purchase/purchase_success")
    public String purchaseSuccess() {
     return "member/purchase/purchase_success";
    }

    @GetMapping("/member/purchase/purchase_fail")
    public String purchaseFail() {
     return "member/purchase/purchase_fail";
    }
}
