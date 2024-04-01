package com._3dhs.tnproject.payment.controller;

import com._3dhs.tnproject.member.dto.MemberDTO;
import com._3dhs.tnproject.member.service.AuthService;
import com._3dhs.tnproject.member.service.MemberService;
import com._3dhs.tnproject.payment.dto.PaymentDTO;
import com._3dhs.tnproject.payment.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class PaymentController {

    private final IamportClient iamportClient;

    @Autowired
    private AuthService authService;

    private final PaymentService paymentService;
    private final MemberService memberService;

    @GetMapping("payment/payment")
    public String getPaymentPage(Model model, Authentication authentication) {
        MemberDTO member = (MemberDTO) authentication.getPrincipal();

        model.addAttribute("loginUserId", member.getMemberId());
        model.addAttribute("member", member);

        return "/payment/payment";
    }

    @Autowired
    public PaymentController(PaymentService paymentService, MemberService memberService) {
        /* key 유출 금지 : 최종 제출 전 반드시 "API_KEY", "API_SECRET"으로 바꿔두기 */
        this.iamportClient = new IamportClient("0308576263486483",
                "Qj5k80IyaxMmWg2pbOKi5rpx2KE8sSQ647gwHaaNm9bqb0YV6l92lhRvkppSAAciwe8Qh5dQe69JCOZK");
        this.paymentService = paymentService;
        this.memberService = memberService;
    }


    @ResponseBody
    @RequestMapping("/verify/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid)
            throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }

    @ResponseBody
    @PostMapping("/paymentSuccess")
    public String paymentSuccess(@RequestParam String imp_uid,
                                  @RequestParam String merchant_uid,
                                  @RequestParam String buyer_name,
                                  @RequestParam int tissuePrice,
                                  Authentication authentication) {

        //유저 정보 업데이트
        MemberDTO currentMember = (MemberDTO) authentication.getPrincipal();
        currentMember.setHaveTissue(currentMember.getHaveTissue() + tissuePrice);
        memberService.updateHaveTissue(currentMember);


        //티슈 정보 추가
        PaymentDTO paymentDTO = new PaymentDTO(
                imp_uid,
                merchant_uid,
                "BUY",
                LocalDateTime.now(),
                tissuePrice,
                currentMember.getMemberCode()
        );
        paymentService.savePaymentList(paymentDTO);

        //사용자 티슈 수 업데이트
        int ntissuePrice = currentMember.getHaveTissue() + tissuePrice;
        memberService.updateHaveTissue(currentMember);

        return "/payment_success";
    }

    @GetMapping("/payment/payment_success")
    public void gotoPaymentSuccessPage() {

    }
}