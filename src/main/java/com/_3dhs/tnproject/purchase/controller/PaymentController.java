package com._3dhs.tnproject.purchase.controller;

import com._3dhs.tnproject.member.service.AuthService;
import com._3dhs.tnproject.purchase.dto.TissueDTO;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class PaymentController {

    private final IamportClient iamportClient;

    private AuthService authService;

    @GetMapping("/purchase/purchase")
    public String getPurchasePage(Model model) {
        // 유저 정보 받기 -> purchase.html로 전달 model에 담아서
        return null;
    }

    public PaymentController() {
        /* key 유출 금지 : 최종 제출 전 반드시 "API_KEY", "API_SECRET"으로 바꿔두기 */
        this.iamportClient = new IamportClient("0308576263486483",
                "Qj5k80IyaxMmWg2pbOKi5rpx2KE8sSQ647gwHaaNm9bqb0YV6l92lhRvkppSAAciwe8Qh5dQe69JCOZK");
    }


    @ResponseBody
    @RequestMapping("/verify/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid)
            throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }

    @ResponseBody
    @PostMapping("/purchase_success")
    public String purchaseSuccess(String imp_uid, String merchant_uid, String buyer_name) {

        System.out.println(imp_uid + merchant_uid + buyer_name);
//        String loginUserId = authService.getCurrentUserId();

        String resultPath = "/purchase/purchase_success.html";

        return resultPath;
    }

}