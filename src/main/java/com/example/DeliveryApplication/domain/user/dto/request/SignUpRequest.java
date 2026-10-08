package com.example.DeliveryApplication.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotBlank(message = "아이디를 입력하셔야 합니다.")
    @Size(min = 4, max = 20, message = "아이디를 4~20자 내로 입력하셔야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호를 입력하셔야 합니다.")
    @Size(min = 8, message = "비밀번호를 8자 이상 입력하셔야합니다.")
    private String password;

    @NotBlank(message = "역할을 입력하셔야 합니다.")
    private String role;
}
