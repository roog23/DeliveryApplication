package com.example.DeliveryApplication.domain.payment.service;

import com.example.DeliveryApplication.domain.order.entity.Order;
import com.example.DeliveryApplication.domain.order.entity.OrderState;
import com.example.DeliveryApplication.domain.order.repository.OrderRepository;
import com.example.DeliveryApplication.domain.payment.entity.Payment;
import com.example.DeliveryApplication.domain.payment.repostiory.PaymentRepository;
import com.example.DeliveryApplication.domain.user.entity.UserRole;
import com.example.DeliveryApplication.global.exception.BaseException;
import com.example.DeliveryApplication.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void paymentOrder(Long orderId, String paymentMethod, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER));

        if(!order.getUser().getId().equals(userId)) {
            throw new BaseException(ErrorCode.NOT_ORDER_USER);
        }

        if(!order.getOrderState().equals(OrderState.ORDER)) {
            throw new BaseException(ErrorCode.NOT_PAYMENT_ORDER);
        }

        long totalPrice = order.getMenu().getPrice() * order.getQuantity();

        Payment payment = new Payment(totalPrice, paymentMethod, order);

        paymentRepository.save(payment);

        order.payment();
    }

    public Page<Payment> searchPayment(Long userId, UserRole role, Pageable pageable) {
        Page<Payment> paymentPage = null;

        if(role.equals(UserRole.CUSTOMER)) {
            paymentPage = paymentRepository.findByOrderUserId(userId, pageable);
        }
        else if(role.equals(UserRole.OWNER)) {
            paymentPage = paymentRepository.findByOrderMenuUserId(userId, pageable);
        }

        return paymentPage;
    }
}
