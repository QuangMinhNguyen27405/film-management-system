package com.crm.controller;

import com.crm.entity.Address;
import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.service.impl.CustomerService;
import com.crm.web.form.LoginForm;
import com.crm.web.form.SignupForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/login")
    public String showAdminLogin(){
        return "admin_login";
    }

    @PostMapping("/login")
    public String doAdminLogin(@ModelAttribute @Valid LoginForm loginForm,
                               HttpServletRequest request, BindingResult result, Model model){
        return "admin_home";
    }

    @GetMapping("/home")
    public String homePage(){
        return "admin_home";
    }

    @GetMapping("/customers/page")
    public String customersHomePage(Model model){
        return findPaginated(1, model);
    }

    @GetMapping("/customers/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,Model model){

        int pageSize = 8;

        Page<Customer> page = customerService.findPaginated(pageNo, pageSize);
        List<Customer> listCustomer = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("customers", listCustomer);

        return "admin_customers";
    }

    @GetMapping("customers/findOne/{customerId}")
    @ResponseBody
    public Customer findOne(@PathVariable Long customerId){
        System.out.println("AdminController - findOne()");
        try {
            return customerService.findCustomer(customerId);
        } catch (RecordNotFoundException ex) {
            return null;
        }
    }

    @PostMapping("customers/save")
    public String updateCustomer(@ModelAttribute @Valid SignupForm signupForm, Model model){
        System.out.println(("Admin Controller - saveCustomer()"));
        try {

            Customer customer = new Customer(signupForm.getFirstName(), signupForm.getLastName(), signupForm.getEmail(), signupForm.getPassword());
            customer.setAddress(new Address(signupForm.getAddress()));
            customer.setCustomerId(signupForm.getCustomerId());

            if(customer.getCustomerId() == null){
                customerService.createCustomer(customer);
            }
            else{
                customerService.updateCustomer(customer.getCustomerId(), customer);
            }
            return "redirect:/admin/customers/page/1";
        } catch (DuplicateEmailException ex){
            return "redirect:/admin/customers/page/1";
        } catch (RecordNotFoundException ex){
            return "redirect:/admin/customers/page/1";
        }
    }
}
