package com._3dhs.tnproject.payment.service;

import com._3dhs.tnproject.payment.dto.PaymentDTO;
import com._3dhs.tnproject.payment.dao.PaymentMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }


    public void savePaymentList(PaymentDTO paymentDTO) {

        paymentMapper.insertPayment(paymentDTO);
    }


}
