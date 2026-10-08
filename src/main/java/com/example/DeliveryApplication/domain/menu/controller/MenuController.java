package com.example.DeliveryApplication.domain.menu.controller;

import com.example.DeliveryApplication.domain.menu.dto.request.MenuRequest;
import com.example.DeliveryApplication.domain.menu.dto.response.MenuResponse;
import com.example.DeliveryApplication.domain.menu.entity.Menu;
import com.example.DeliveryApplication.domain.menu.service.MenuService;
import com.example.DeliveryApplication.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuRequest request,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Menu menu = menuService.createMenu(request.getName(), request.getPrice(), request.getInfo(), userDetails.getId());
        MenuResponse response = new MenuResponse(menu.getId(), menu.getMenuName(), menu.getPrice(), menu.getMenuInfo(), menu.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<MenuResponse>> searchMenu(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Menu> menuList = menuService.searchMenu(pageable);

        Page<MenuResponse> responses = menuList
                .map(menu -> new MenuResponse(
                        menu.getId(),
                        menu.getMenuName(),
                        menu.getPrice(),
                        menu.getMenuInfo(),
                        menu.getUser().getId()));

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> searchMenuId(@PathVariable Long menuId) {
        Menu menu = menuService.searchMenuId(menuId);

        MenuResponse response = new MenuResponse(
                menu.getId(),
                menu.getMenuName(),
                menu.getPrice(),
                menu.getMenuInfo(),
                menu.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long menuId,
                                        @RequestBody MenuRequest request,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Menu menu = menuService.updateMenu(menuId, request.getName(), request.getPrice(), request.getInfo(), userDetails.getId());

        MenuResponse response = new MenuResponse(
                menu.getId(),
                menu.getMenuName(),
                menu.getPrice(),
                menu.getMenuInfo(),
                menu.getUser().getId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        menuService.deleteMenu(menuId,userDetails.getId());

        return ResponseEntity.ok("메뉴가 삭제되었습니다.");
    }
}
