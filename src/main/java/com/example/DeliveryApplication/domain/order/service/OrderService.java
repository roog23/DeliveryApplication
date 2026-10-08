package com.example.DeliveryApplication.domain.order.service;

import com.example.DeliveryApplication.domain.menu.entity.Menu;
import com.example.DeliveryApplication.domain.menu.repository.MenuRepository;
import com.example.DeliveryApplication.domain.order.entity.Order;
import com.example.DeliveryApplication.domain.order.entity.OrderState;
import com.example.DeliveryApplication.domain.order.repository.OrderRepository;
import com.example.DeliveryApplication.domain.user.entity.User;
import com.example.DeliveryApplication.domain.user.entity.UserRole;
import com.example.DeliveryApplication.domain.user.repository.UserRepository;
import com.example.DeliveryApplication.global.exception.BaseException;
import com.example.DeliveryApplication.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public Order createOrder(Long menuId, Long userId, Integer quantity, String address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USERID));

        if(user.getRole().equals(UserRole.OWNER)){
            throw new BaseException(ErrorCode.NOT_ROLE_CUSTOMER);
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_MENU));

        if(menu.getMenuDelete()){
            throw new BaseException(ErrorCode.DELETE_MENU);
        }

        Long totalPrice = menu.getPrice() * quantity;

        Order order = new Order(user, menu, quantity, totalPrice, address);

        orderRepository.save(order);

        return order;
    }

    public Page<Order> searchPageOrder(Long id, UserRole role, Pageable pageable) {
        Page<Order> orderList = null;

        if(role.equals(UserRole.CUSTOMER)){
            orderList = orderRepository.findByUserId(id, pageable);
        }
        else if(role.equals(UserRole.OWNER)) {
            orderList = orderRepository.findByMenuUserId(id, pageable);
        }

        return orderList;
    }

    public Order searchOrder(Long orderId, Long userId, UserRole role) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER));

        if(role.equals(UserRole.CUSTOMER) && !order.getUser().getId().equals(userId)) {
            throw new BaseException(ErrorCode.NOT_ORDER_USER);
        }
        else if(role.equals(UserRole.OWNER) && !order.getMenu().getUser().getId().equals(userId)) {
            throw new BaseException(ErrorCode.NOT_ORDER_USER);
        }

        return order;
    }

    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER));

        if(!order.getUser().getId().equals(userId)){
            throw new BaseException(ErrorCode.NOT_ORDER_USER);
        }

        if(!order.getOrderState().equals(OrderState.ORDER)){
            throw new BaseException(ErrorCode.NOT_CANCEL_ORDER);
        }

        order.cancelOrder();
    }

    @Transactional
    public Order changeOrderState(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_ORDER));

        if(!order.getMenu().getUser().getId().equals(userId)){
            throw new BaseException(ErrorCode.NOT_ORDER_USER);
        }

        if(!(order.getOrderState().equals(OrderState.PAYMENT_OK) ||
        order.getOrderState().equals(OrderState.ORDER_OK))){
            throw new BaseException(ErrorCode.NOT_CHANGE_ORDER);
        }

        order.changeState(order);

        return order;
    }


}
