package com.app.Service;

import com.app.DTO.PaymentRequestDTO;
import com.app.Entity.Payment;

public interface PaymentService {

	Payment processPayment(PaymentRequestDTO dto);

}
