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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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


@Log4j2
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

        log.info("CustomerController - doSignup()");

        try {
            //Call Service Layer
            Customer newCustomer = customerService.createCustomer(customer);
            if (newCustomer != null) {
                log.info("Sign Up Successfully");
            }
            return "redirect:/login";

        } catch (DuplicateEmailException ex){
            return "redirect:/signup";
        }
    }

    @GetMapping("/login")
    private String showLoginForm(Model model,
                                 @RequestParam(value = "status", required = false) String status){

        Authentication auth = securityUtils.getUserAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            return "redirect:/home";
        }

        model.addAttribute("login", false);
        model.addAttribute("pageTitle", "Log In");

        if (status != null) {
            model.addAttribute(status, true);
        }

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute @Valid Customer customer,
                           BindingResult result,
                           HttpServletRequest request){

        if(result.hasErrors()){
            return "redirect:/login?status=login_error";
        }

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
            String status = new String("invalid_email_password");
            // Redirect to Log In Error URL
            return "redirect:/login?status=" + status;
        } catch (Exception e){
            String status = new String("login_error");
            return "redirect:/login?status=" + status;
        }

    }

    @GetMapping("/customer/profile/{customerId}")
    public String showUpdateForm(@RequestParam Long customerId){
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

        log.info("CustomerController - updateCustomer()");
        try {
            customerService.updateCustomer(customer);
            return "redirect:/profile/" + customerId;
        } catch (RecordNotFoundException ex){
            return "redirect:/profile/" + customerId;
        } catch (DuplicateEmailException ex) {
            return "redirect:/profile/" + customerId;
        }
    }

}
