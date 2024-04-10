package com._3dhs.tnproject.payment.dao;

import com._3dhs.tnproject.payment.dto.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    void insertPayment(PaymentDTO paymentDTO);

    List<String> selectPaymentList(int memberCode);
}
