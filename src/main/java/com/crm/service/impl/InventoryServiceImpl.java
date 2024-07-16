package com.crm.service.impl;

import com.crm.entity.Inventory;
import com.crm.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InventoryServiceImpl {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> fetchInventory(){
        return inventoryRepository.findAll();
    }

    public Inventory createInventory(Inventory inventory){
        return inventoryRepository.save(inventory);
    }

    public Inventory findInventoryById(Long inventoryId){
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        return inventory.get();
    }
}
