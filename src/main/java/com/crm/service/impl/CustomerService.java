package com.crm.service.impl;

import com.crm.entity.Address;
import com.crm.entity.Customer;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.AddressRepository;
import com.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Customer> fetchCustomers(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer){
        //find for duplicate address
        String address = customer.getAddress().getAddress();
        Optional<Address> dbAddress = addressRepository.findByAddress(address);
        dbAddress.ifPresent(customer::setAddress);

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long customerId, Customer customer){
        //check if the customer existed in the database
        Optional<Customer> dbCustomer = customerRepository.findById(customerId);
        if(dbCustomer.isEmpty()){
            throw new RecordNotFoundException("Customer with id " + customerId + " does not exist");
        }
        //find for duplicate address
        String address = customer.getAddress().getAddress();
        Optional<Address> dbAddress = addressRepository.findByAddress(address);
        dbAddress.ifPresent(customer::setAddress);
        //check if the changed email matches with any other email
        Customer existedEmail = customerRepository.findByEmaiExcludeId(customer.getEmail(), customerId);
        if(existedEmail != null){
            throw new DuplicateEmailException("Email " + customer.getEmail() + " existed");
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId){
        //check if the customer existed in the database
        Optional<Customer> dbCustomer = customerRepository.findById(customerId);
        if(dbCustomer.isEmpty()){
            throw new RecordNotFoundException("Customer with id " + customerId + " does not exist");
        }
        customerRepository.deleteById(customerId);
    }

    public Customer signIn(String email, String password){
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null && password.equals( customer.getPassword() ) ){
            return customer;
        }
        return null;
    }

    public Page<Customer> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.customerRepository.findAll(pageable);
    }
}
