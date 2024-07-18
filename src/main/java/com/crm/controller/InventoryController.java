package com.crm.controller;

import com.crm.entity.Inventory;
import com.crm.entity.Inventory;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.InventoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/inventories")
    public List<Inventory> fetchCustomers (){
        log.info("InventoryController - fetchInventory()");
        return inventoryRepository.findAll();
    }

    @PostMapping("/inventories")
    public List<Inventory> createInventory(@RequestBody List<Inventory> inventories){ //@Request Body: Lay file JSON
        log.info("InventoryController - createInventory()");
        return inventoryRepository.saveAll(inventories);
    }

    @PutMapping("/inventories/{inventoryId}")
    public Inventory updateInventory(@PathVariable Long inventoryId, @RequestBody Inventory inventory){
        log.info("InventoryController - updateInventory()");
        Optional<Inventory> dbInventory = inventoryRepository.findById(inventoryId);
        if(dbInventory.isEmpty()){
            throw new RecordNotFoundException("Inventory with id " + inventoryId + " does not exist");
        }
        return inventoryRepository.save(inventory);
    }

    @DeleteMapping("/inventories/{inventoryId}")
    public String deleteInventory(@PathVariable Long inventoryId){
        log.info("InventoryController - deleteInventory()");
        Optional<Inventory> dbInventory = inventoryRepository.findById(inventoryId);
        if(dbInventory.isEmpty()){
            throw new RecordNotFoundException("Inventory with id" + inventoryId + " does not exist");
        }
        inventoryRepository.deleteById(inventoryId);
        return "Inventory[id = " + inventoryId + "] was deleted.";
    }
}
