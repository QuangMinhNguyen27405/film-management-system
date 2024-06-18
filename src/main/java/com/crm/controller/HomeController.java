package com.crm.controller;

import com.crm.web.form.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(HttpServletRequest request) {

        System.out.println("Session id = " + request.getRequestedSessionId());

        HttpSession session = request.getSession(true);

        LoginForm userLogin = (LoginForm) session.getAttribute("userSession");

        System.out.println("Hello = " + userLogin);
        return "home";
    }

}
