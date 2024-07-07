package com.crm.controller;

import com.crm.entity.Film;
import com.crm.security.SecurityUtils;
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

@Controller
@CrossOrigin("*")
@RequestMapping
public class FilmController
{
    @Autowired
    private FilmService filmService;

    @Autowired
    SecurityUtils securityUtils;

    @GetMapping("/film/{filmId}")
    public String showFilmDetails(@PathVariable Long filmId, Model model){

        Film film = filmService.findFilm(filmId);
        if(film != null) {
            model.addAttribute("film", film);
            model.addAttribute("pageTitle", film.getTitle());
        }
        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        return "single";
    }
}
