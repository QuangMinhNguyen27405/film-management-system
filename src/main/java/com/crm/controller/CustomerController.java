package com.crm.controller;

import com.crm.entity.Address;
import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.service.impl.CustomerService;
import com.crm.web.form.LoginForm;
import com.crm.web.form.SignupForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/signup")
    public String showSignupForm(HttpServletRequest request, Model model){
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
        model.addAttribute("pageTitle", "Log In");
        return "login";
    }

//    @PostMapping("/login")
//    private String doLogin(@ModelAttribute @Valid LoginForm loginForm,
//                           BindingResult result, HttpServletRequest request, Model model){
//
//        System.out.println("CustomerController - doLogin()");
//
//        if(result.hasErrors()){
//            model.addAttribute("error", "Invalid email or password.");
//            return "login";
//        }
//
//        Customer customer = customerService.signIn(loginForm.getEmail(), loginForm.getPassword());
//        if(customer != null){
//            System.out.println("Login Successfully");
//
//            HttpSession session = request.getSession(true);
//            session.setAttribute("userSession", loginForm);
//
//            System.out.println("Redirect to home page");
//            return "redirect:/home";
//        }
//        else{
//            model.addAttribute("error", "Invalid email or password.");
//            return "login";
//        }
//    }

    @GetMapping("/profile/{customerId}")
    private String updateForm(@RequestParam Long customerId){
        return "profile/" + customerId;
    }

    @PostMapping("/profile/update")
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

    @DeleteMapping("/profile/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId){
        System.out.println("CustomerController - deleteCustomer()");
        try {
            customerService.deactivateCustomer(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RecordNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
