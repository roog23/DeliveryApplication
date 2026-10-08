package com.example.DeliveryApplication.domain.payment.dto.response;

import com.example.DeliveryApplication.domain.order.entity.OrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private Long totalPrice;
    private String paymentMethod;
    private OrderState orderState;
}
