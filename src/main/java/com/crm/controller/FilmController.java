package com.crm.controller;

import com.crm.entity.Category;
import com.crm.entity.Film;
import com.crm.security.SecurityUtils;
import com.crm.service.impl.CategoryService;
import com.crm.service.impl.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin("*")
public class FilmController
{
    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    SecurityUtils securityUtils;

    @GetMapping("/films/film/{filmId}")
    public String showFilmDetails(@PathVariable Long filmId, Model model){
        System.out.println("showFilmDetails filmId = " + filmId);

        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        Film film = filmService.findFilm(filmId);
        if(film != null) {
            model.addAttribute("film", film);
            model.addAttribute("pageTitle", film.getTitle());
        }

        return "single";
    }

    @GetMapping("/films/{categoryName}")
    public String showFilm(@PathVariable String categoryName, Model model){
        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        List<Category> categoryList = categoryService.fetchCategory();
        List<Film> filmList = filmService.findFilmByCategory(categoryName);

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("filmList", filmList);
        model.addAttribute("selectedCategory", categoryName);

        return "rent.html";
    }
}
