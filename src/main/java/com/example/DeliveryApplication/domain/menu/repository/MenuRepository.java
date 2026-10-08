package com.example.DeliveryApplication.domain.menu.repository;

import com.example.DeliveryApplication.domain.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByIdAndMenuDeleteFalse(Long menuId);

    Page<Menu> findAllByMenuDeleteFalse(Pageable pageable);
}
