package com.example.DeliveryApplication.domain.payment.repostiory;

import com.example.DeliveryApplication.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @EntityGraph(attributePaths = {"order"})
    Page<Payment> findByOrderUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"order"})
    Page<Payment> findByOrderMenuUserId(Long userId, Pageable pageable);
}
