package com.crm.controller;

import com.crm.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {

    @Autowired
    SecurityUtils securityUtils;

    @GetMapping("/home")
    public String homePage(HttpServletRequest request, Model model) {

        log.info("Customer Home Page");

        model.addAttribute("pageTitle", "Home Page");

        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        return "home";
    }

    @GetMapping("/about")
    public String aboutPage(HttpServletRequest request, Model model) {

        log.info("Customer About Page");

        model.addAttribute("pageTitle", "About Page");

        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        return "about";
    }

    @GetMapping("/joinus")
    public String joinus(HttpServletRequest request, Model model) {

        log.info("Customer Joinus Page");

        model.addAttribute("pageTitle", "Joinus Page");

        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        return "joinus";
    }
}
