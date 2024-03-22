package com._3dhs.tnproject.purchase.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class PaymentController {

    private final IamportClient iamportClient;

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

}