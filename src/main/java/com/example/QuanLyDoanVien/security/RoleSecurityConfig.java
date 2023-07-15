package com.example.QuanLyDoanVien.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.example.QuanLyDoanVien.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
// @Order(1)
public class RoleSecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    // @Bean
    // public UserDetailsService userDetailsService() {
    // return new CustomUserDetailsService();
    // }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // @Bean
    // public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
    // http.authorizeRequests().requestMatchers("/").permitAll();

    // http.securityMatcher("/admin/**")
    // .authorizeRequests().anyRequest().hasAuthority("ADMIN")
    // .and()
    // .formLogin()
    // .loginPage("/admin/login")
    // .usernameParameter("email")
    // .loginProcessingUrl("/admin/login")
    // .defaultSuccessUrl("/admin/danh-sach-chi-doan")
    // .failureUrl("/admin/login?error")
    // .permitAll()
    // .and()
    // .logout()
    // .logoutUrl("/admin/logout")
    // .logoutSuccessUrl("/");

    // return http.build();
    // }

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers("/").permitAll();
        http.authorizeRequests()
            .requestMatchers("/admin/**").hasAuthority("ADMIN")
            .requestMatchers("/can-bo/**").hasAuthority("CANBO")
            .requestMatchers("/doan-vien/**").hasAuthority("DOANVIEN")
            // .anyRequest().authenticated()
            // .requestMatchers("/").hasAnyAuthority("ADMIN", "CANBO", "DOANVIEN")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            // .loginPage("/login")
            // .usernameParameter("email")
            .successHandler(authenticationSuccessHandler) // Set the custom authentication success handler
            .failureHandler(authenticationFailureHandler) // Set the custom authentication failure handler
            // .failureForwardUrl("login?error")
            .permitAll()
            .and()
        .logout()
            .permitAll()
            .and()
        .sessionManagement()
            .maximumSessions(1)
            .sessionRegistry(sessionRegistry())
            .expiredUrl("/login?expired") // Redirect to login page if session expires
            .and()
            .and()
            .csrf().disable();

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(authProvider()));
    }
}
