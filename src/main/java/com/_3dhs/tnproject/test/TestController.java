package com._3dhs.tnproject.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final MessageSourceAccessor messageSourceAccessor;
    private final PasswordEncoder encoder;

    @GetMapping("/error/global")
    public String testErrorMessage(RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", messageSourceAccessor.getMessage("error.global"));
        return "/error/global";
    }

    @GetMapping("/layout/common_layout")
    public void goto_common_layout() {
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

    @GetMapping("/common/makepassword")
    public void gotoMakePasswordPage() {
    }
    @PostMapping("/common/makepassword")
    public String makePassword(String pass, Model model) {
        String password = encoder.encode(pass);
        model.addAttribute("password", password);
        return "/common/makepassword";
    }
}
