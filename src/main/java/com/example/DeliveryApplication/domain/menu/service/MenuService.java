package com.example.DeliveryApplication.domain.menu.service;

import com.example.DeliveryApplication.domain.menu.entity.Menu;
import com.example.DeliveryApplication.domain.menu.repository.MenuRepository;
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
public class MenuService {
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public Menu createMenu(String name, Long price, String info, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USERID));

        if(!user.getRole().equals(UserRole.OWNER)) {
            throw new BaseException(ErrorCode.NOT_ROLE_CUSTOMER);
        }

        Menu menu = new Menu(name, price, info, user);

        menuRepository.save(menu);

        return menu;
    }

    public Page<Menu> searchMenu(Pageable pageable) {
        return menuRepository.findAllByMenuDeleteFalse(pageable);
    }

    public Menu searchMenuId(Long menuId) {
        return menuRepository.findByIdAndMenuDeleteFalse(menuId)
                .orElseThrow(() -> new BaseException((ErrorCode.NOT_FOUND_MENU)));
    }

    @Transactional
    public Menu updateMenu(Long menuId, String name, Long price, String info, Long id) {
        Menu menu = menuCheck(menuId, id);

        menu.updateMenu(name, price, info);

        return menu;
    }


    @Transactional
    public void deleteMenu(Long menuId, Long id) {
        Menu menu = menuCheck(menuId, id);

        menu.deleteMenu();
    }

    private Menu menuCheck(Long menuId, Long id) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BaseException((ErrorCode.NOT_FOUND_MENU)));

        if(!menu.getUser().getId().equals(id)){
            throw new BaseException(ErrorCode.NOT_MENU_USER);
        }
        if(menu.getMenuDelete()) {
            throw new BaseException(ErrorCode.DELETE_MENU);
        }

        return menu;
    }

}
