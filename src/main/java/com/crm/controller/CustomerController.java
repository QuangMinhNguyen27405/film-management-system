package com.crm.controller;

import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.security.JwtService;
import com.crm.security.SecurityUtils;
import com.crm.service.impl.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin("*")
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    SecurityUtils securityUtils = new SecurityUtils();

    Authentication auth = securityUtils.getUserAuthentication();

    @GetMapping("/signup")
    public String showSignupForm(HttpServletRequest request, Model model){
        if(auth != null && auth.isAuthenticated()){
            return "redirect:/home";
        }
        model.addAttribute("pageTitle", "Sign Up");
        model.addAttribute("login", false);
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@ModelAttribute @Valid Customer customer,
                           HttpServletRequest request, Model model, BindingResult result){
        System.out.println("CustomerController - doSignup()");

        try {
            //Call Service Layer
            Customer newCustomer = customerService.createCustomer(customer);
            if (newCustomer != null) {
                System.out.println("Sign Up Successfully");
            }
            return "login";

        } catch (DuplicateEmailException ex){
            return "login";
        }
    }

    @GetMapping("/login")
    private String showLoginForm(HttpServletRequest request, Model model){
        if(auth != null && auth.isAuthenticated()){
            return "redirect:/home";
        }
        model.addAttribute("login", false);
        model.addAttribute("pageTitle", "Log In");

        return "login";
    }

    @GetMapping("/login?error")
    private String showLoginFormError(HttpServletRequest request, Model model){
        if(auth.isAuthenticated()){
            return "redirect:/home";
        }

        model.addAttribute("pageTitle", "Log In");
        model.addAttribute("param.error", true);
        return "login";
    }

    @PostMapping("/login")
    private String doLogin(@ModelAttribute @Valid Customer customer, BindingResult result,
                           HttpServletRequest request, Model model){

        System.out.println("CustomerController - doLogin()");

        try {
            // Throws exception if the credential is not correct
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword()));

            // Generate token
            String token = jwtService.generateToken(customer.getEmail());
            // Store token in the session
            request.getSession().setAttribute("token", token);
            // Redirect to Home Page
            return "redirect:/home";
        } catch (AuthenticationException e) {
            // Redirect to Log In Error URL
            return "redirect:/login?error";
        }

    }

    @GetMapping("/customer/profile/{customerId}")
    private String showUpdateForm(@RequestParam Long customerId){
        if(auth != null && auth.isAuthenticated()){
            return "redirect:/login";
        }
        return "profile";
    }

    @PostMapping("/customer/profile/update")
    public String doUpdate(@ModelAttribute @Valid Customer customer,
                           @RequestParam Long customerId,
                           HttpServletRequest request,
                           BindingResult result, Model model){

        System.out.println("CustomerController - updateCustomer()");
        try {
            customerService.updateCustomer(customer);
            return "profile/" + customerId;
        } catch (RecordNotFoundException ex){
            return "profile/" + customerId;
        } catch (DuplicateEmailException ex) {
            return "profile/" + customerId;
        }
    }

}
