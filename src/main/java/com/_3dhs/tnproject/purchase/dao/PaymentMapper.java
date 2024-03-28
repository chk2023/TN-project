package com._3dhs.tnproject.purchase.dao;

import com._3dhs.tnproject.purchase.dto.TissueDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    void insertPayment(TissueDTO tissueDTO);
}
