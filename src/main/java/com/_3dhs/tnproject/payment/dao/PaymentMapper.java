package com._3dhs.tnproject.payment.dao;

import com._3dhs.tnproject.payment.dto.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    void insertPayment(PaymentDTO paymentDTO);
}
