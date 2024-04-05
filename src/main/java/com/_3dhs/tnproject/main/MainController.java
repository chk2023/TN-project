package com._3dhs.tnproject.main;


import com._3dhs.tnproject.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {
    @GetMapping({"/main", "/"})
    public String mainPage(Authentication authentication,
                            @AuthenticationPrincipal MemberDTO member){
        if (authentication != null && authentication.isAuthenticated()) {
            String id = member.getMemberId();
            String nickname = member.getProfile().getProfileNickname();

            log.info("id : " + id);
            log.info("nickname : " + nickname);

            if (id.equals(nickname)) {
                return "redirect:/timeline/list";
            } else {
                return "redirect:/timeline/list";
            }
        }
        return "member/login";
    }
}
