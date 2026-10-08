package com.example.DeliveryApplication.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ALREADY_USERID(HttpStatus.CONFLICT, "이미 사용중인 아이디입니다."),
    NOT_FOUND_USERID(HttpStatus.UNAUTHORIZED, "없는 아이디입니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),

    NOT_ROLE_CUSTOMER(HttpStatus.FORBIDDEN, "CUSTOMER만 사용 가능합니다." ),
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "없는 메뉴입니다." ),
    NOT_MENU_USER(HttpStatus.FORBIDDEN, "본인 메뉴가 아닙니다."),
    DELETE_MENU(HttpStatus.NOT_FOUND, "삭제된 메뉴입니다."),

    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "없는 주문입니다." ),
    NOT_ORDER_USER(HttpStatus.FORBIDDEN, "본인의 주문이 아닙니다."),
    NOT_CANCEL_ORDER(HttpStatus.BAD_REQUEST, "주문 요청 상태일 때만 취소가 가능합니다." ),
    NOT_CHANGE_ORDER(HttpStatus.BAD_REQUEST, "상태 변경이 불가능한 상태입니다." ),

    NOT_PAYMENT_ORDER(HttpStatus.BAD_REQUEST, "결제 불가능한 상태입니다." );

    private final HttpStatus status;
    private final String message;

}
