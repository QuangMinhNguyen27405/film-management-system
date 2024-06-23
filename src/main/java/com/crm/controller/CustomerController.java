package com.crm.controller;

import com.crm.entity.Address;
import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.service.impl.CustomerService;
import com.crm.web.form.LoginForm;
import com.crm.web.form.SignupAndUpdateForm;
import com.crm.web.form.SignupForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/signup")
    public String signupForm(HttpServletRequest request, Model model){
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@ModelAttribute SignupForm signupForm,
                           HttpServletRequest request, Model model, BindingResult result){
        System.out.println("CustomerController - doSignup()");
        System.out.println(signupForm.getFirstName() + signupForm.getLastName() + signupForm.getEmail());

        try {
            //duplicate SignupForm into Customer
            Customer tempCustomer = new Customer(
                    signupForm.getFirstName(),
                    signupForm.getLastName(),
                    signupForm.getEmail(),
                    signupForm.getPassword());
            Address address = new Address();
            address.setAddress(signupForm.getAddress());
            tempCustomer.setAddress(address);

            //Call Service Layer
            Customer customer = customerService.createCustomer(tempCustomer);
            if (customer != null) {
                System.out.println("Sign Up Successfully");
            }
            return "login";

        } catch (DuplicateEmailException ex){
            return "login";
        }
    }

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

    @GetMapping("/profile/{customerId}")
    private String updateForm(@RequestParam Long customerId){
        return "profile/" + customerId;
    }

    @PutMapping("/profile/{customerId}")
    public String doUpdate(@ModelAttribute @Valid SignupAndUpdateForm signupAndUpdateForm,
                           @RequestParam Long customerId,
                           HttpServletRequest request,
                           BindingResult result, Model model){

        System.out.println("CustomerController - updateCustomer()");
        try {

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
            customerService.deleteCustomer(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RecordNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
