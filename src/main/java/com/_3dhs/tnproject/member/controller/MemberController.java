package com._3dhs.tnproject.member.controller;

import com._3dhs.tnproject.common.exceptionhandler.member.MemberRegistException;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.AuthService;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

//    @PostMapping("/loginfail")
//    public String loginFailed(RedirectAttributes rttr) {
//        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("error.login"));
//        return "redirect:/";
//    }

    @GetMapping("/regist")
    public void registPage(){}

    @PostMapping("/regist")
    public String registMember(MemberDTO member, RedirectAttributes rttr) throws MemberRegistException {
        member.setMemberPwd(passwordEncoder.encode(member.getPassword()));

        log.info("Request regist member : {}", member);

        memberService.registMember(member);

        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.regist"));

        return "redirect:/";
    }

    protected Authentication createNewAuthentication(String memberId) {
        UserDetails newPrincipal = authenticationService.loadUserByUsername(memberId);
        UsernamePasswordAuthenticationToken newAuth
                = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(),
                newPrincipal.getAuthorities());
        return newAuth;
    }
}
