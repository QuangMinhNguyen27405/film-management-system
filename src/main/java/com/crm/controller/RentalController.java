package com.crm.controller;

import com.crm.entity.Rental;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.RentalRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @PostMapping("/rentals")
    public List<Rental> createRental(@RequestBody List<Rental> rentals){ //@Request Body: Lay file JSON
        log.info("RentalController - createRental()");
        return rentalRepository.saveAll(rentals);
    }

    @PutMapping("/rentals/{rentalId}")
    public Rental updateRental(@PathVariable Long rentalId, @RequestBody Rental rental){
        log.info("RentalController - updateRental()");
        Optional<Rental> dbRental = rentalRepository.findById(rentalId);
        if(dbRental.isEmpty()){
            throw new RecordNotFoundException("Rental with id " + rentalId + " does not exist");
        }
        return rentalRepository.save(rental);
    }

    @DeleteMapping("/rentals/{rentalId}")
    public String deleteRental(@PathVariable Long rentalId){
        log.info("RentalController - deleteRental()");
        Optional<Rental> dbRental = rentalRepository.findById(rentalId);
        if(dbRental.isEmpty()){
            throw new RecordNotFoundException("Rental with id" + rentalId + " does not exist");
        }
        rentalRepository.deleteById(rentalId);
        return "Rental[id = " + rentalId + "] was deleted.";
    }
}
