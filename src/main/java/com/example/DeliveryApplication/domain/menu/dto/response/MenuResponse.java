package com.example.DeliveryApplication.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponse {
    private Long id;
    private String name;
    private Long price;
    private String info;
    private Long userId;
}
