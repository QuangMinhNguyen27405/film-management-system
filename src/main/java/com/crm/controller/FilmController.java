package com.crm.controller;

import com.crm.entity.Category;
import com.crm.entity.Film;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.CategoryRepository;
import com.crm.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/films")
    private List<Film> fetchFilm(){
        System.out.println("FilmControl - fetchFilm()");
        return filmRepository.findAll();
    }

    @PostMapping("/films")
    private List<Film> createFilm(@RequestBody List<Film> films){
        System.out.println("FilmControl - createFilm()");
        return filmRepository.saveAll(films);
    }

    @PutMapping("/films/{filmId}")
    private Film updateFilm(@PathVariable Long filmId, @RequestBody Film film){
        System.out.println("FilmControl - updateFilm()");
        Optional<Film> dbFilm = filmRepository.findById(filmId);
        if(dbFilm.isEmpty()){
            throw new RecordNotFoundException("Film with id " + filmId + " does not exist.");
        }
        return filmRepository.save(film);
    }

    @DeleteMapping("films/{filmId}")
    private String deleteFilm(@PathVariable Long filmId){
        System.out.println("FilmControl - updateFilm()");
        Optional<Film> dbFilm = filmRepository.findById(filmId);
        if(dbFilm.isEmpty()){
            throw new RecordNotFoundException("Film with id " + filmId + " does not exist.");
        }
        filmRepository.deleteById(filmId);
        return "Film with id " + filmId + " deleted.";
    }
}
