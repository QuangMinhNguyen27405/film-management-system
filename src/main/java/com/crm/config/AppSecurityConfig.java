package com.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
//                .authorizeHttpRequests(request-> request.requestMatchers("/css/*", "/js/*", "/dummy/*", "/images/*", "/fonts/*").permitAll())
                .authorizeRequests()
                .requestMatchers("/customer/signup").permitAll()
                .requestMatchers("/customer/login").permitAll()
                .requestMatchers("/home").permitAll()
                .and()
                .formLogin(form -> form.loginPage("/customer/login").permitAll())
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}
