package com.crm.security;

import com.crm.entity.Customer;
import com.crm.entity.Role;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null){
            System.out.println("Found User With Email " + email + " and Password " + customer.getPassword());
            return new UserPrincipal(customer);
        } else {
            System.out.println("Not Found User With Email " + email);
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }


}
