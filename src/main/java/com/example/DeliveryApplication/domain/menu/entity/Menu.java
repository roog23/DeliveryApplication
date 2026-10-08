package com.example.DeliveryApplication.domain.menu.entity;

import com.example.DeliveryApplication.domain.user.entity.User;
import com.example.DeliveryApplication.global.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "menus")
@NoArgsConstructor
public class Menu extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Long price;

    private String menuInfo;
    private Boolean menuDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Menu(String menuName, Long price, String menuInfo, User user) {
        this.menuName = menuName;
        this.price = price;
        this.menuInfo = menuInfo;
        this.user = user;
        menuDelete = false;
    }

    public void updateMenu(String name, Long price, String info) {
        this.menuName = name;
        this.price = price;
        this.menuInfo = info;
    }

    public void deleteMenu() {
        menuDelete = true;
    }
}
