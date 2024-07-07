package com.crm.service.impl;

import com.crm.entity.Actor;
import com.crm.entity.Customer;
import com.crm.entity.Film;
import com.crm.entity.Language;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.ActorRepository;
import com.crm.repository.FilmRepository;
import com.crm.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ActorRepository actorRepository;

    public Film createFilm(Film film){
        System.out.println("service");
        //find for exisiting actors
        List<Actor> actors = film.getActors();
        List<Actor> filmActor = new ArrayList<>();
        for (Actor actor : actors){
            Optional<Actor> dbActor = actorRepository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName());
            if(dbActor.isPresent()){
                filmActor.add(dbActor.get());
            }
            else{
                actorRepository.save(actor);
                filmActor.add(actor);
            }
        }
        film.setActors(filmActor);
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

    public Page<Film> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.filmRepository.findAll(pageable);
    }
}
