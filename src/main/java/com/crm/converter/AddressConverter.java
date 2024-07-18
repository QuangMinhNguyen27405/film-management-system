package com.crm.converter;

import com.crm.entity.Address;
import com.crm.repository.AddressRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class AddressConverter implements Converter<String, Address> {
    //TODO MAKE ADDRESS CONVERTER, FINISH CUSTOMER ADD AND EDIT, FINISH SIGN UP AND LOG IN FORM

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address convert(@NotNull String address) {
        log.info("Trying to convert string of " + address + " into an address");

        return new Address(address);
    }
}
