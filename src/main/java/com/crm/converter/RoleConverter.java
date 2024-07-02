package com.crm.converter;

import com.crm.entity.Role;
import com.crm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role convert(String roleId) {
        System.out.println("Trying to find role with id " + roleId);

        Long parseId = Long.parseLong(roleId);
        Optional<Role> role = roleRepository.findById(parseId);

        if(role.isPresent()){
            return role.get();
        }
        return null;
    }
}
