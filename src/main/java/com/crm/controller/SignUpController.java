package com.crm.controller;

import com.crm.entity.Address;
import com.crm.entity.Customer;
import com.crm.service.impl.CustomerService;
import com.crm.web.form.SignupForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@CrossOrigin("*")
public class SignUpController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/signup")
    public String signupForm(HttpServletRequest request){
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@ModelAttribute @Valid SignupForm signupForm,
                           HttpServletRequest request, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("error", "There is an error creating the account");
        }

        //duplicate SignupForm into Customer
        Customer tempCustomer = new Customer(signupForm.getFirstName(),
                                            signupForm.getLastName(),
                                            signupForm.getEmail(),
                                            signupForm.getPassword());
        Address address = new Address();
        address.setAddress(signupForm.getAddress());
        tempCustomer.setAddress(address);

        //Call Service Layer
        Customer customer = customerService.createCustomer(tempCustomer);
        if(customer != null){
            System.out.println("Sign Up Successfully");
        }

        return "signup";
    }
}
