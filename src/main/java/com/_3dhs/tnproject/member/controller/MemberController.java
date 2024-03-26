package com._3dhs.tnproject.member.controller;

import com._3dhs.tnproject.common.exceptionhandler.member.MemberRegistException;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.AuthService;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final MessageSourceAccessor messageSourceAccessor;

    @Autowired
    public MemberController(MemberService memberService, AuthService authenticationService, PasswordEncoder passwordEncoder, MessageSourceAccessor messageSourceAccessor) {
        this.memberService = memberService;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @GetMapping(value = {"/login"})
    public void loginPage(){}

    @PostMapping("/loginfail")
    public String loginFailed(RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("error.login"));
        return "redirect:/";
    }

    @GetMapping("/regist")
    public void registPage(){}

    @PostMapping("/idDupCheck")
    public ResponseEntity<String> checkDuplication(@RequestBody MemberDTO member) {

        log.info("Request Check ID : {}", member.getMemberId());

        String result = "사용 가능한 아이디입니다.";

        if(memberService.selectMemberById(member.getMemberId())) {
            result = "중복 된 아이디가 존재합니다.";
        }

        return ResponseEntity.ok(result);

    }

    @PostMapping("/regist")
    public String registMember(MemberDTO member, @RequestParam("optionalId") String optionalId, RedirectAttributes rttr) throws MemberRegistException {
        String memberId = member.getMemberId() + optionalId;

        if (!"default".equals(optionalId)) member.setMemberId(memberId);

        member.setMemberPwd(passwordEncoder.encode(member.getPassword()));

        log.info("Request regist member : {}", member);

        memberService.registMember(member);

        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.regist"));

        return "redirect:/common/testhub";
    }

    @GetMapping("/info")
    public void memberPage(){}

    protected Authentication createNewAuthentication(String memberId) {
        UserDetails newPrincipal = authenticationService.loadUserByUsername(memberId);
        UsernamePasswordAuthenticationToken newAuth
                = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(),
                newPrincipal.getAuthorities());
        return newAuth;
    }
}
