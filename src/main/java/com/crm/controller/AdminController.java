package com.crm.controller;

import com.crm.entity.Customer;
import com.crm.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@CrossOrigin("*")
public class AdminController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/admin_dashboard")
    public String dashboard(Model model){

        List<Customer> customers = customerService.fetchCustomers();
        model.addAttribute("customers", customers);
        return "admin_dashboard";
    }
}
