package com.crm.controller;


import com.crm.web.form.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
public class LogInController {

    @GetMapping("/login")
    private String longinForm(HttpServletRequest request){
        return "login";
    }

    @PostMapping("/login")
    private String doLogin(@ModelAttribute @Valid LoginForm loginForm,
                           HttpServletRequest request, BindingResult result){

        System.out.println("Redirect to home page");

        HttpSession session = request.getSession(true);
        session.setAttribute("userSession", loginForm);

        return "redirect:/home";

    }
}
