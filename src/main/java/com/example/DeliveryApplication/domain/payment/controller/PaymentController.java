package com.example.DeliveryApplication.domain.payment.controller;

import com.example.DeliveryApplication.domain.payment.dto.request.PaymentRequest;
import com.example.DeliveryApplication.domain.payment.dto.response.PaymentResponse;
import com.example.DeliveryApplication.domain.payment.entity.Payment;
import com.example.DeliveryApplication.domain.payment.service.PaymentService;
import com.example.DeliveryApplication.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<String> paymentOrder(@PathVariable Long orderId,
                                          @RequestBody PaymentRequest request,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        paymentService.paymentOrder(orderId, request.getPaymentMethod(), userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("결제 완료 되었습니다.");
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> searchPayment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentPage= paymentService.searchPayment(userDetails.getId(), userDetails.getUserRole() ,pageable);

        Page<PaymentResponse> responses = paymentPage
                .map(payment -> new PaymentResponse(
                        payment.getId(),
                        payment.getOrder().getId(),
                        payment.getTotalPrice(),
                        payment.getPaymentMethod(),
                        payment.getOrder().getOrderState()));

        return ResponseEntity.ok(responses);
    }
}
