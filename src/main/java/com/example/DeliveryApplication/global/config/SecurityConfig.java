package com.example.DeliveryApplication.global.config;

import com.example.DeliveryApplication.global.exception.CustomAccessDeniedHandler;
import com.example.DeliveryApplication.global.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/signup", "/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/menus").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT,"/menus/**", "/orders/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE,"/menus/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST,"/orders/**","/payments/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE,"/orders/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling((exception) -> exception
                                       .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
