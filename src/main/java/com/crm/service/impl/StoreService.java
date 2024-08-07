package com.crm.service.impl;

import com.crm.entity.Address;
import com.crm.entity.Store;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.AddressRepository;
import com.crm.repository.StoreRepository;
import org.antlr.v4.runtime.RecognitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Store> fetchStores(){
        return storeRepository.findAll();
    }

    public Store createStore(Store store){
        //find for existing address
        if(store.getAddress() != null) {
            String address = store.getAddress().getAddress();
            Optional<Address> dbAddress = addressRepository.findByAddress(address);
            dbAddress.ifPresent(store::setAddress);
        }
        return storeRepository.save(store);
    }

    public Store findStoreById(Long storeId){
        Optional<Store> dbStore = storeRepository.findById(storeId);
        if(dbStore.isEmpty()) {
            throw new RecordNotFoundException("Store with id " + storeId + " not found");
        }
        return dbStore.get();
    }
}
