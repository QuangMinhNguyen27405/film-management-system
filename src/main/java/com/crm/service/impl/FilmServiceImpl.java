package com.crm.service.impl;

import com.crm.entity.*;
import com.crm.exception.custom.OutOfStockException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.*;
import com.crm.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FilmServiceImpl {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private InventoryRepository inventoryRepository;


    public List<Film> fetchFilms(){
        return filmRepository.findAll();
    }

    public Film createFilm(Film film){
        //find for exisiting actors
        if(film.getActors() != null) {
            List<Actor> actors = film.getActors();
            List<Actor> filmActor = new ArrayList<>();
            for (Actor actor : actors) {
                Optional<Actor> dbActor = actorRepository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName());
                if (dbActor.isPresent()) {
                    filmActor.add(dbActor.get());
                } else {
                    actorRepository.save(actor);
                    filmActor.add(actor);
                }
            }
            film.setActors(filmActor);
        }
        //find categories
        if(film.getCategories() != null){
            List<Category> categoryList = film.getCategories();
            List<Category> filmCategory = new ArrayList<>();
            for (Category category : categoryList){
                Optional<Category> dbCategory = categoryRepository.findByName(category.getName());
                if(dbCategory.isPresent()){
                    filmCategory.add(dbCategory.get());
                }
                else{
                    throw new RecordNotFoundException();
                }
            }
            film.setCategories(filmCategory);
        }
        return filmRepository.save(film);
    }

    public Film updateFilm(Film film){
        return filmRepository.save(film);
    }

    public Film findFilm(Long filmId){
        Optional<Film> dbFilm = filmRepository.findById(filmId);
        if(dbFilm.isEmpty()){
            throw new RecordNotFoundException();
        }
        return dbFilm.get();
    }

    public List<Film> findFilmByCategory(String name){
        return filmRepository.findAllByCategory(name);
    }

    public Page<Film> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.filmRepository.findAll(pageable);
    }

    public String rentFilm(Long filmId, Long storeId) {
        try {
            System.out.println("Rent Film with id" + filmId);
            String userEmailLoggedIn = securityUtils.getUserLoggedIn();
            System.out.println("User email:" + userEmailLoggedIn);

            Rental rental = new Rental();

            rental.setCustomer(customerService.findCustomerByEmail(userEmailLoggedIn));
            rental.setInventory(findInventoryByFilm(filmId));
            rental.setStore(storeService.findStoreById(storeId));
            rental.setRentalDate(LocalDate.now());

            rentalRepository.save(rental);
        } catch (RecordNotFoundException recordNotFoundException){
            return "not_signed_in";
        } catch (OutOfStockException outOfStockException){
            return "out_of_stock";
        }
        return "success";
    }

    private Inventory findInventoryByFilm(Long filmId){

        List<Inventory> dbInventoryList = inventoryRepository.findByFilmId(filmId);
        if(dbInventoryList.isEmpty()){
            throw new OutOfStockException();
        }
        for(Inventory inventory : dbInventoryList){
            if(inventory.getRentals() != null){
                return inventory;
            }
        }

        throw new RecordNotFoundException();
    }
}
