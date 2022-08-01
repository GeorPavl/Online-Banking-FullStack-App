package com.service;

import com.dto.PaymentDTO;
import com.entity.Payment;

public interface PaymentService {

    Payment dtoToEntity(PaymentDTO paymentDTO);
}
