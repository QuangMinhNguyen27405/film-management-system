package com.crm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    SimpleUrlAuthenticationSuccessHandler userSuccessHandler
            = new SimpleUrlAuthenticationSuccessHandler("/home");

    SimpleUrlAuthenticationSuccessHandler adminSuccessHandler
            = new SimpleUrlAuthenticationSuccessHandler("/admin/customers/page");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority authority : authorities) {
            String authorityName = authority.getAuthority();
            if(authorityName.equals("ROLE_ADMIN")) {
                this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }
        }
        this.userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }
}
