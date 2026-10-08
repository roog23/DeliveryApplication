package com.example.DeliveryApplication.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequest {
    @NotNull(message = "수량을 입력해주세요.")
    private Integer quantity;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

}
