package com.crm.controller;

import com.crm.entity.Customer;
import com.crm.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    CustomerService customerService;

//    @GetMapping("/customers/page")
//    public String customersHomePage(Model model){
//        return findPaginated(1, model);
//    }
//
//    @GetMapping("/customers/{pageNo}")
//    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,Model model){
//
////        List<Customer> customers = customerService.fetchCustomers();
////        model.addAttribute("customers", customers);
////        return "admin/customers";
//
//        int pageSize = 5;
//
//        Page<Customer> page = customerService.findPaginated(pageNo, pageSize);
//        List<Customer> listCustomer = page.getContent();
//
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("customers", listCustomer);
//
//        return "admin/customers";
//    }
//

}
