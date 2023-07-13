package com.example.QuanLyDoanVien.security;
// package com.example.SpringSecurity.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.SecurityFilterChain;

// import com.example.SpringSecurity.controller.AppController;
// import com.example.SpringSecurity.entity.User;
// import com.example.SpringSecurity.service.CustomUserDetailsService;

// @Configuration
// @Order(2)
// public class CanboSecurityConfig {
//     @Bean
//     public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
//         http.authorizeRequests().requestMatchers("/").permitAll();
        
//         http.securityMatcher("/can-bo/**")
//                 .authorizeRequests().anyRequest().hasAuthority("CANBO")
//                 .and()
//                 .formLogin()
//                 .loginPage("/can-bo/login")
//                 .usernameParameter("email")
//                 .loginProcessingUrl("/can-bo/login")
//                 .defaultSuccessUrl("/can-bo/danh-sach-doan-vien")
//                 .permitAll()
//                 .and()
//                 .logout()
//                 .logoutUrl("/can-bo/logout")
//                 .logoutSuccessUrl("/");

//         return http.build();
//     }
// }
