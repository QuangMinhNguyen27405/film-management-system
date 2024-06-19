package com.crm.controller;


import com.crm.entity.Customer;
import com.crm.service.impl.CustomerService;
import com.crm.web.form.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
public class LogInController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    private String longinForm(HttpServletRequest request){
        return "login";
    }

    @PostMapping("/login")
    private String doLogin(@ModelAttribute @Valid LoginForm loginForm,
                           HttpServletRequest request, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }

        Customer customer = customerService.signIn(loginForm.getEmail(), loginForm.getPassword());
        if(customer != null){
            System.out.println("Login Successfully");

            HttpSession session = request.getSession(true);
            session.setAttribute("userSession", loginForm);

            System.out.println("Redirect to home page");
            return "redirect:/home";
        }
        else{
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }

}
