package com.crm.security;

import com.crm.entity.Customer;
import com.crm.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> dbCustomer = customerRepository.findByEmailActive(email);
        if(dbCustomer.isPresent()){
            Customer customer = dbCustomer.get();
            log.info("Found User With Email " + email + " and Password " + customer.getPassword());
            return new UserPrincipal(customer);
        } else {
            log.info("Not Found User With Email " + email);
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }


}
