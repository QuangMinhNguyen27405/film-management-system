package com.crm.service.impl;

import com.crm.entity.Address;
import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.AddressRepository;
import com.crm.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Log4j2
@Component
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Customer> fetchCustomers(){
        return customerRepository.findAll();
    }

    public Customer findCustomer(Long customerId) {
        Optional<Customer> dbCustomer = customerRepository.findById(customerId);
        if(dbCustomer.isEmpty()){
            throw new RecordNotFoundException();
        }
        return dbCustomer.get();
    }

    public Customer createCustomer(Customer customer){
        // Customer must be active
        if(!customer.isActive()){
            customer.setActive(true);
        }
        // find for existing address
        if(customer.getAddress() != null) {
            String address = customer.getAddress().getAddress();
            Optional<Address> dbAddress = addressRepository.findByAddress(address);
            dbAddress.ifPresent(customer::setAddress);
        }
        // encode user password
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer){
        //check if the customer existed in the database
        Optional<Customer> dbCustomer = customerRepository.findById(customer.getCustomerId());
        if(dbCustomer.isEmpty()){
            throw new RecordNotFoundException("Customer with id " + customer.getCustomerId() + " does not exist");
        }
        //find for duplicate address
        String address = customer.getAddress().getAddress();
        Optional<Address> dbAddress = addressRepository.findByAddress(address);
        dbAddress.ifPresent(customer::setAddress);
        //check if the changed email matches with any other email
        Customer existedEmail = customerRepository.findByEmaiExcludeId(customer.getEmail(), customer.getCustomerId());
        if(existedEmail != null){
            throw new DuplicateEmailException("Email " + customer.getEmail() + " existed");
        }
        return customerRepository.save(customer);
    }

    public void deactivateCustomer(Long customerId){
        //check if the customer existed in the database
        Optional<Customer> dbCustomer = customerRepository.findById(customerId);
        if(dbCustomer.isEmpty()){
            throw new RecordNotFoundException("Customer with id " + customerId + " does not exist");
        }
        Customer customer = dbCustomer.get();
        if(customer.isActive()){
            customer.setActive(false);
        }
        else{
            customer.setActive(true);
        }
        customerRepository.save(customer);
    }

    public Page<Customer> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.customerRepository.findAll(pageable);
    }

    public Customer findCustomerByEmail(String email){
        Optional<Customer> dbCustomer = customerRepository.findByEmailActive(email);
        if(dbCustomer.isEmpty()){
            log.info("Not found customer with email " + email + " with active status");
            throw new RecordNotFoundException("Customer with email " + email + " does not exist");
        }
        return dbCustomer.get();
    }
}
