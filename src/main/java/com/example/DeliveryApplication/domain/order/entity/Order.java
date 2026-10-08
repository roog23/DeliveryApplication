package com.example.DeliveryApplication.domain.order.entity;

import com.example.DeliveryApplication.domain.menu.entity.Menu;
import com.example.DeliveryApplication.domain.user.entity.User;
import com.example.DeliveryApplication.global.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer quantity;
    private Long totalPrice;
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    public Order(User user, Menu menu, Integer quantity, Long totalPrice, String address) {
        this.user = user;
        this.menu = menu;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.address = address;
        orderState = OrderState.ORDER;
    }

    public void cancelOrder() {
        orderState = OrderState.ORDER_CANCEL;
    }

    public void changeState(Order order) {
        if(order.getOrderState().equals(OrderState.PAYMENT_OK)) {
            this.orderState = OrderState.ORDER_OK;
        }
        else if(order.getOrderState().equals(OrderState.ORDER_OK)) {
            this.orderState = OrderState.DELIVERY;
        }
    }

    public void payment() {
        orderState = OrderState.PAYMENT_OK;
    }
}
