package com.example.DeliveryApplication.domain.user.service;

import com.example.DeliveryApplication.domain.user.entity.User;
import com.example.DeliveryApplication.domain.user.entity.UserRole;
import com.example.DeliveryApplication.domain.user.repository.UserRepository;
import com.example.DeliveryApplication.global.exception.BaseException;
import com.example.DeliveryApplication.global.exception.ErrorCode;
import com.example.DeliveryApplication.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String signUp(String id, String password, String role) {
        if(userRepository.findByLoginId(id).isPresent()){
            throw new BaseException(ErrorCode.ALREADY_USERID);
        }
        User user = new User(id, passwordEncoder.encode(password), UserRole.valueOf(role));
        userRepository.save(user);
        return jwtUtil.createToken(user.getId(), user.getLoginId(), user.getRole());
    }

    public String login(String id,  String password) {
        User user = userRepository.findByLoginId(id)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USERID));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BaseException(ErrorCode.WRONG_PASSWORD);
        }

        return jwtUtil.createToken(user.getId(),user.getLoginId(), user.getRole());
    }
}
