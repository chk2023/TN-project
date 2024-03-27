package com._3dhs.tnproject.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;  // 의존성 주입을 통해 필요한 객체를 가져옴
    private static final String senderEmail = "tissunetwork@gmail.com";
    private static String code;

    public static String createCode(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        code = "";
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) Math.floor(Math.random() * characters.length());
            code += characters.charAt(randomIndex);
        }
        return code;
    }

    public MimeMessage createMail(String memberId){
        createCode();

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, memberId);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + code + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String sendMail(String memberId) {
        MimeMessage message = createMail(memberId);

        javaMailSender.send(message);

        return code;
    }
}