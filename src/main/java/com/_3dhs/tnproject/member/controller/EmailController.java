//package com._3dhs.tnproject.member.controller;
//
//import com._3dhs.tnproject.member.service.EmailService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Controller
//public class EmailController {
//    private final EmailService emailService;
//
//    @PostMapping("/emailConfirm")
//    public String mailConfirm(@RequestBody JoinRequest joinRequest) {
//        int num = emailService.sendEmail(joinRequest.getId());
//
//        return "코드 발급" + num;
//    }
//}
