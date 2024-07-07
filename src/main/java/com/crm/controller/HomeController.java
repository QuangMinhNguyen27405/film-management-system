package com.crm.controller;

import com.crm.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    SecurityUtils securityUtils;

    @GetMapping("/home")
    public String homePage(HttpServletRequest request, Model model) {

        System.out.println("Customer Home Page");

        HttpSession session = request.getSession(true);
        model.addAttribute("pageTitle", "Home Page");

        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        return "home";
    }
}
