package com.example.DeliveryApplication.domain.menu.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuRequest {
    @NotBlank(message = "메뉴 이름은 작성해야합니다.")
    private String name;

    @Min(value = 1, message = "1 이상의 금액을 입력해주세요.")
    @NotNull(message = "가격을 입력해주세요.")
    private Long price;
    private String info;
}
