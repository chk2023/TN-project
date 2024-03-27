package com._3dhs.tnproject.member.controller;

import com._3dhs.tnproject.member.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String sendMail(@RequestBody Map<String, String> requestBody) {
        String memberId = requestBody.get("memberId");
        String code = mailService.sendMail(memberId);

        return code;
    }
}
