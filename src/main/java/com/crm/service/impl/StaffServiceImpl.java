package com.crm.service.impl;

import com.crm.entity.*;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.AddressRepository;
import com.crm.repository.StaffRepository;
import com.crm.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StoreRepository storeRepository;

    public List<Staff> fetchStaffs(){
        return staffRepository.findAll();
    }

    public Staff createStaff(Staff staff){
        //find for existing address
        String address = staff.getAddress().getAddress();
        Optional<Address> dbAddress = addressRepository.findByAddress(address);
        dbAddress.ifPresent(staff::setAddress);
        //find for existing store
        String store = staff.getStore().getAddress().getAddress();
        Optional<Store> dbStoreAddress = storeRepository.findByAddress(address);
        dbStoreAddress.ifPresent(staff::setStore);

        return staffRepository.save(staff);
    }

    public Staff updateStaff(Staff staff){
        //find for existing address
        String address = staff.getAddress().getAddress();
        Optional<Address> dbAddress = addressRepository.findByAddress(address);
        dbAddress.ifPresent(staff::setAddress);
        //find for existing store
        String store = staff.getStore().getAddress().getAddress();
        Optional<Store> dbStoreAddress = storeRepository.findByAddress(address);
        dbStoreAddress.ifPresent(staff::setStore);
        //check if the customer existed in the database
        Optional<Staff> dbStaff = staffRepository.findById(staff.getStaffId());
        if(dbStaff.isEmpty()){
            throw new RecordNotFoundException("Customer with id " + staff.getStaffId() + " does not exist");
        }
        //check if the changed email matches with any other email
        Staff existedEmail = staffRepository.findByEmaiExcludeId(staff.getEmail(), staff.getStaffId());
        if(existedEmail != null){
            throw new DuplicateEmailException("Email " + staff.getEmail() + " existed");
        }
        return staffRepository.save(staff);
    }

    public Page<Staff> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.staffRepository.findAll(pageable);
    }
}
