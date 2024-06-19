package com.crm.controller;

import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.AddressRepository;
import com.crm.repository.CustomerRepository;
import com.crm.service.impl.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> fetchCustomers (HttpServletRequest request){
        System.out.println("CustomerController - fetchCustomer()");
        return ResponseEntity.ok(customerService.fetchCustomers());
    }

    @PostMapping("/customer/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){ //@Request Body: Lay file JSON
        System.out.println("CustomerController - createCustomer()");
        try {
            Customer createdCustomer = customerService.createCustomer(customer);
            return ResponseEntity.ok(createdCustomer);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer){
        System.out.println("CustomerController - updateCustomer()");
        try {
            return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
        } catch (RecordNotFoundException ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DuplicateEmailException ex) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/customer/{customerId}")
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
