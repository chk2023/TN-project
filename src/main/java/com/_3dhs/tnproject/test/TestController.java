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

    @GetMapping("/layout/02_layout")
    public void goto02_layout() {

    }
    @GetMapping("/layout/04_layout")
    public void goto04_layout() {
    }
    @GetMapping(value = {"/","/main"})
    public String gotoTestHubPage() {
        return "/common/testhub";
    }
}
