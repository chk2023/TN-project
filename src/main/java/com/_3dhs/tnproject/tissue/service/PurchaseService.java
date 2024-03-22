//package com._3dhs.tnproject.tissue.service;
//
//import com._3dhs.tnproject.tissue.dto.PurchaseConfirmationRequest;
//import com._3dhs.tnproject.tissue.dto.PurchaseConfirmationResponse;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class PurchaseService {
//
//    private final RestTemplate restTemplate;
//
//    // 생성자 주입을 통해 RestTemplate을 주입받습니다.
//    public PurchaseService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public PurchaseConfirmationResponse confirmPurchase(PurchaseConfirmationRequest request) {
//        // 외부 API 호출을 위한 URL 설정
//        String apiUrl = "https://api.tosspayments.com/v1/payments/confirm";
//
//        // 외부 API 호출 및 응답을 받아옵니다.
//        PurchaseConfirmationResponse response = restTemplate.postForObject(apiUrl, request, PurchaseConfirmationResponse.class);
//
//        return response;
//    }
//
//
////    public Map<String, Object> confirmPurchase() {
////        // 여기에 실제 결제 확인 처리 로직을 구현합니다.
////        // 예를 들어, 데이터베이스에서 결제 정보를 확인하고 결과를 반환할 수 있습니다.
////        Map<String, Object> result = new HashMap<>();
////        result.put("message", "Payment confirmed successfully");
////        return result;
////    }
//}