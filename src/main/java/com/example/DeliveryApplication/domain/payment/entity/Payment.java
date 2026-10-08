package com.example.DeliveryApplication.domain.payment.entity;

import com.example.DeliveryApplication.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "payments")
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalPrice;
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public Payment(long totalPrice, String paymentMethod, Order order) {
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.order = order;
    }
}
