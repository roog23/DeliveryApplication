package com.example.DeliveryApplication.domain.order.repository;

import com.example.DeliveryApplication.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"menu","user"})
    Page<Order> findByUserId(Long id, Pageable pageable);

    @EntityGraph(attributePaths = {"menu","user"})
    Page<Order> findByMenuUserId(Long id, Pageable pageable);
}
