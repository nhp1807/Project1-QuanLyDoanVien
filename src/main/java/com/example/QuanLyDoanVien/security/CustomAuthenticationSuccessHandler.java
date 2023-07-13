package com.example.QuanLyDoanVien.security;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                if (roles.contains("ADMIN")) {
                    response.sendRedirect("/admin/danh-sach-chi-doan");
                } else if (roles.contains("CANBO")) {
                    response.sendRedirect("/can-bo/danh-sach-doan-vien");
                }else if(roles.contains("DOANVIEN")){
                    response.sendRedirect("/doan-vien/danh-sach-doan-vien");
                } else {
                    // Handle other roles or scenarios
                    response.sendRedirect("/login"); // Redirect to the default page
                }
    }
    
}
