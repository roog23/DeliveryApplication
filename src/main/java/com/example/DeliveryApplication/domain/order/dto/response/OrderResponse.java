package com.example.DeliveryApplication.domain.order.dto.response;

import com.example.DeliveryApplication.domain.order.entity.OrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long ownerId;
    private Long userId;
    private String menuName;
    private Integer quantity;
    private Long totalPrice;
    private String address;
    private OrderState State;
}
