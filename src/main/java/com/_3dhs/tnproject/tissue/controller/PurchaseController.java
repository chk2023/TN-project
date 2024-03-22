package com._3dhs.tnproject.tissue.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping(value="/")
public class PurchaseController {

    @GetMapping(value = "/purchase_success")
    public String paymentSuccess(
            Model model,
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey) throws Exception {

        String secretKey = "test_sk_zXLkKEypNArWmo50nX3lmeaxYG5R:";

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(secretKey.getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        JsonObject obj = new JsonObject();
        obj.addProperty("orderId", orderId);
        obj.addProperty("amount", amount);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;
        model.addAttribute("isSuccess", isSuccess);

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(reader).getAsJsonObject();
        responseStream.close();
        model.addAttribute("responseStr", jsonObject.toString());
        System.out.println(jsonObject.toString());

        model.addAttribute("method", jsonObject.get("method").getAsString());
        model.addAttribute("orderName", jsonObject.get("orderName").getAsString());

        if (jsonObject.get("method") != null) {
            String method = jsonObject.get("method").getAsString();
            if (method.equals("카드")) {
                model.addAttribute("cardNumber", jsonObject.getAsJsonObject("card").get("number").getAsString());
            } else if (method.equals("가상계좌")) {
                model.addAttribute("accountNumber", jsonObject.getAsJsonObject("virtualAccount").get("accountNumber").getAsString());
            } else if (method.equals("계좌이체")) {
                model.addAttribute("bank", jsonObject.getAsJsonObject("transfer").get("bank").getAsString());
            } else if (method.equals("휴대폰")) {
                model.addAttribute("customerMobilePhone", jsonObject.getAsJsonObject("mobilePhone").get("customerMobilePhone").getAsString());
            }
        } else {
            model.addAttribute("code", jsonObject.get("code").getAsString());
            model.addAttribute("message", jsonObject.get("message").getAsString());
        }

        return "purchase_success";
    }

    @GetMapping(value = "/purchase_fail")
    public String paymentFail(
            Model model,
            @RequestParam(value = "message") String message,
            @RequestParam(value = "code") Integer code
    ) throws Exception {

        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return "purchase_fail";
    }
}
