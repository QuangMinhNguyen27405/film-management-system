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
        System.out.println("User Detail");
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null){
            return new org.springframework.security.core.userdetails.User(
                    customer.getEmail(), customer.getPassword()
                    , mapRolesToAuthorities(customer.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
        return  mapRoles;
    }
}
