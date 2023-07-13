package com.example.QuanLyDoanVien.security;
// package com.example.SpringSecurity.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @Order(3)
// public class DoanvienSecurityConfig {
//     @Bean
//     public SecurityFilterChain filterChain3(HttpSecurity http) throws Exception {
//         http.authorizeRequests().requestMatchers("/").permitAll();
        
//         http.securityMatcher("/doan-vien/**")
//                 .authorizeRequests().anyRequest().hasAuthority("DOANVIEN")
//                 .and()
//                 .formLogin()
//                 .loginPage("/doan-vien/login")
//                 .usernameParameter("email")
//                 .loginProcessingUrl("/doan-vien/login")
//                 .defaultSuccessUrl("/doan-vien/danh-sach-doan-vien")
//                 .permitAll()
//                 .and()
//                 .logout()
//                 .logoutUrl("/doan-vien/logout")
//                 .logoutSuccessUrl("/");

//         return http.build();
//     }
// }
