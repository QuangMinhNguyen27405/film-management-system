package com.crm.controller;

import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.security.JwtService;
import com.crm.security.SecurityUtils;
import com.crm.service.impl.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


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

    @Autowired
    SecurityUtils securityUtils;

    @GetMapping("/signup")
    public String showSignupForm(HttpServletRequest request, Model model){
        Authentication auth = securityUtils.getUserAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
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
            return "redirect:/login";

        } catch (DuplicateEmailException ex){
            return "redirect:/signup";
        }
    }

    @GetMapping("/login")
    private String showLoginForm(HttpServletRequest request, Model model){
        Authentication auth = securityUtils.getUserAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            return "redirect:/home";
        }
        model.addAttribute("login", false);
        model.addAttribute("pageTitle", "Log In");

        return "login";
    }

    @GetMapping("/login?error")
    private String showLoginFormError(HttpServletRequest request, Model model){
        Authentication auth = securityUtils.getUserAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
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
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword());
            Authentication authentication = authenticationManager.authenticate(authReq);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext .setAuthentication(authentication);

            HttpSession session = request.getSession();

            // Generate token
            String token = jwtService.generateToken(customer.getEmail());

            // Store token in the session
            session.setAttribute("token", token);

            // Set security context in session
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
            // Redirect to Home Page
            return "redirect:/home";
        } catch (AuthenticationException e) {
            // Redirect to Log In Error URL
            return "redirect:/login?error";
        } catch (Exception e){
            return "redirect:/login?error";
        }

    }

    @GetMapping("/customer/profile/{customerId}")
    private String showUpdateForm(@RequestParam Long customerId){
        Authentication auth = securityUtils.getUserAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
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
