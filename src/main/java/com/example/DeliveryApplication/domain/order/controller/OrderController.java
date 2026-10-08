package com.example.DeliveryApplication.domain.order.controller;

import com.example.DeliveryApplication.domain.order.dto.request.OrderRequest;
import com.example.DeliveryApplication.domain.order.dto.response.OrderResponse;
import com.example.DeliveryApplication.domain.order.entity.Order;
import com.example.DeliveryApplication.domain.order.service.OrderService;
import com.example.DeliveryApplication.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{menuId}")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long menuId,
                                         @Valid @RequestBody OrderRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Order order = orderService.createOrder(menuId, userDetails.getId(), request.getQuantity(), request.getAddress());

        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getMenu().getUser().getId(),
                order.getUser().getId(),
                order.getMenu().getMenuName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getOrderState());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> searchPageOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.searchPageOrder(userDetails.getId(), userDetails.getUserRole(), pageable);

        Page<OrderResponse> responses = orders
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getMenu().getUser().getId(),
                        order.getUser().getId(),
                        order.getMenu().getMenuName(),
                        order.getQuantity(),
                        order.getTotalPrice(),
                        order.getAddress(),
                        order.getOrderState()));

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> searchOrder(@PathVariable Long orderId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Order order = orderService.searchOrder(orderId, userDetails.getId(), userDetails.getUserRole());

        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getMenu().getUser().getId(),
                order.getUser().getId(),
                order.getMenu().getMenuName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getOrderState());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.cancelOrder(orderId, userDetails.getId());
        return ResponseEntity.ok("주문 취소가 완료되었습니다.");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> changeOrderState(@PathVariable Long orderId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Order order = orderService.changeOrderState(orderId, userDetails.getId());

        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getMenu().getUser().getId(),
                order.getUser().getId(),
                order.getMenu().getMenuName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getOrderState());

        return ResponseEntity.ok(response);
    }
}
