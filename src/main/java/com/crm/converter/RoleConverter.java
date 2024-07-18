package com.crm.converter;

import com.crm.entity.Role;
import com.crm.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

@Log4j2
public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role convert(String roleId) {
        log.info("Trying to find role with id " + roleId);

        Long parseId = Long.parseLong(roleId);
        Optional<Role> role = roleRepository.findById(parseId);

        if(role.isPresent()){
            return role.get();
        }
        return null;
    }
}
