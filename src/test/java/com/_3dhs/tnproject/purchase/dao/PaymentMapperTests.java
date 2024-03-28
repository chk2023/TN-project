package com._3dhs.tnproject.purchase.dao;

import com._3dhs.tnproject.purchase.dto.TissueDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class PaymentMapperTests {

    @Autowired
    PaymentMapper paymentMapper;

    @Test
    void 입력값_테스트() {
        TissueDTO tissueDTO = new TissueDTO(
                123456789,
                "오더클래스",
                LocalDateTime.now(),
                20000,
                1
        );

        paymentMapper.insertPayment(tissueDTO);

    }
}
