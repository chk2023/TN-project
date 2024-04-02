package com._3dhs.tnproject.member.controller;

import com._3dhs.tnproject.common.exceptionhandler.member.MemberRegistException;
import com._3dhs.tnproject.common.exceptionhandler.member.MemberRemoveException;
import com._3dhs.tnproject.common.exceptionhandler.member.MemberUpdateException;
import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.AuthService;
import com._3dhs.tnproject.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String loginPage(Authentication authentication){
        Object user = authentication;
        if (user != null) {
            return "redirect:/timeline/list";
        }
        return "member/login";
    }

    @PostMapping("/loginfail")
    public String loginFailed(RedirectAttributes rttr) {
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("error.login"));
        return "redirect:/member/login";
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

    @PostMapping("/idCheck")
    public ResponseEntity<String> checkId(@RequestBody MemberDTO member) {

        log.info("Request Check ID : {}", member.getMemberId());

        String result = "가입되지 않은 회원입니다.";

        if(memberService.selectMemberById(member.getMemberId())) {
            result = "인증 코드 전송을 눌러.";
        }

        return ResponseEntity.ok(result);
    }


    @PostMapping("/regist")
    public String registMember(MemberDTO member,
                               @RequestParam("optionalId") String optionalId,
                               @RequestParam("memberPwd") String memberPwd,
                               RedirectAttributes rttr) throws MemberRegistException {
        // 이메일 도메인 까지 추가하여 db에 입력
        String memberId = member.getMemberId() + optionalId;
        if (!"default".equals(optionalId)) member.setMemberId(memberId);

        // 비밀번호 BCrypt 해싱처리하여 db에 입력
        member.setMemberPwd(passwordEncoder.encode(memberPwd));

        log.info("Request regist member : {}", member);

        memberService.registMember(member);

        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.regist"));

        return "redirect:/member/login";
    }

    @GetMapping("/update")
    public void updatePage(){}

    @PostMapping("/update")
    public String updateMember(MemberDTO updateMember,
                               @AuthenticationPrincipal MemberDTO loginMember) throws MemberUpdateException {

        updateMember.setMemberCode(loginMember.getMemberCode());

        log.info("updateMember request Member : {}", updateMember);

        memberService.updateMember(updateMember);

        /* 로그인 시 저장된 Authentication 객체를 변경된 정보로 교체한다. */
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(loginMember.getMemberId()));

        return "redirect:/common/testhub";
    }

    protected Authentication createNewAuthentication(String memberId) {
        UserDetails newPrincipal = authenticationService.loadUserByUsername(memberId);
        UsernamePasswordAuthenticationToken newAuth
                = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(),
                newPrincipal.getAuthorities());
        return newAuth;
    }

    @GetMapping("/delete")
    public String deleteMember(@AuthenticationPrincipal MemberDTO member) throws MemberRemoveException {
        log.info("login member : {}", member);

        member.setMemberStatus("DELETE");
        member.setIsDeleted(true);

        memberService.deleteMember(member);

        return "redirect:/member/logout";
    }

    @GetMapping("/findPwd")
    public void findPwd(){};

    @PostMapping("/findPwd")
    public String updatePwd(MemberDTO member,
                            @RequestParam("memberId") String memberId,
                            @RequestParam("optionalId") String optionalId,
                            @RequestParam("memberPwd") String memberPwd,
                            RedirectAttributes rttr) throws MemberUpdateException {

        // 이메일 도메인 까지 추가하여 db에 입력
        String fullMemberId = memberId + optionalId;
        if (!"default".equals(optionalId)) {
            member.setMemberId(fullMemberId);
        } else {
            member.setMemberId(memberId);
        }

        // 비밀번호 BCrypt 해싱처리하여 db에 입력
        member.setMemberPwd(passwordEncoder.encode(memberPwd));
        
        log.info(member.toString());

        memberService.updatePwd(member);

        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.updatePwd"));

        return "redirect:/member/login";
    }


//    @PostMapping("/regist")
//    public String registMember(MemberDTO member,
//                               @RequestParam("optionalId") String optionalId,
//                               @RequestParam("memberPwd") String memberPwd,
//                               RedirectAttributes rttr) throws MemberRegistException {
//        // 이메일 도메인 까지 추가하여 db에 입력
//        String memberId = member.getMemberId() + optionalId;
//        if (!"default".equals(optionalId)) member.setMemberId(memberId);
//
//        // 비밀번호 BCrypt 해싱처리하여 db에 입력
//        member.setMemberPwd(passwordEncoder.encode(memberPwd));
//
//        log.info("Request regist member : {}", member);
//
//        memberService.registMember(member);
//
////        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.regist"));
//
//        return "redirect:/";
//    }
    /* 비밀번호 재설정 */
//    @PostMapping("/findPwd")
//    public String updatePwd(@RequestBody Map<String, String> requestBody,
//                            RedirectAttributes rttr) {
//        String memberId = requestBody.get("memberId");
//        String currentPwd = requestBody.get("currentPwd");
//        String newPwd = requestBody.get("newPwd");
//        log.info(memberId);
//
//        MemberDTO member = memberService.getMemberbyId(memberId);
//
//        if (!currentPwd.equals(member.getMemberPwd())) {
//            rttr.addFlashAttribute("error", messageSourceAccessor.getMessage("현재 비밀번호가 잘못 입력되었습니다."));
//        } else {
//            member.setMemberPwd(newPwd);
//            rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("member.updatePwd"));
//        }
//
//        return "redirect:/member/login";
//    }
}
