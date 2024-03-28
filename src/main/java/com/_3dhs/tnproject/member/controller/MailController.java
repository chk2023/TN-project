package com._3dhs.tnproject.member.controller;

import com._3dhs.tnproject.member.service.MailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String sendMail(@RequestBody Map<String, String> requestBody) {
        String memberId = requestBody.get("memberId");
        String generatedCode = mailService.sendMail(memberId);

        Map<String, String> param = new HashMap<>();
        param.put("generatedCode",generatedCode);

        return generatedCode;
    }
}
