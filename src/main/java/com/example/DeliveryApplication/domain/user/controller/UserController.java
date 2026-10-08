package com.example.DeliveryApplication.domain.user.controller;

import com.example.DeliveryApplication.domain.user.dto.request.LoginRequest;
import com.example.DeliveryApplication.domain.user.dto.request.SignUpRequest;
import com.example.DeliveryApplication.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest request) {
        String token = userService.signUp(request.getId(), request.getPassword(), request.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request.getId(), request.getPassword());
        return ResponseEntity.ok(token);
    }

}
