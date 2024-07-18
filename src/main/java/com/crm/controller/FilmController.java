package com.crm.controller;

import com.crm.entity.Category;
import com.crm.entity.Film;
import com.crm.entity.Store;
import com.crm.security.SecurityUtils;
import com.crm.service.impl.CategoryService;
import com.crm.service.impl.FilmServiceImpl;
import com.crm.service.impl.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
public class FilmController
{
    @Autowired
    private FilmServiceImpl filmServiceImpl;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    SecurityUtils securityUtils;

    @Autowired
    private StoreService storeService;

    @GetMapping("/films/{categoryName}")
    public String showFilm(@PathVariable String categoryName, Model model){
        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        List<Category> categoryList = categoryService.fetchCategory();
        List<Film> filmList = filmServiceImpl.findFilmByCategory(categoryName);

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("filmList", filmList);
        model.addAttribute("selectedCategory", categoryName);

        return "films";
    }

    @GetMapping("/films/film/{filmId}")
    public String showFilmDetails(@PathVariable Long filmId, Model model,
                                  @RequestParam(value = "status", required = false) String status){
        System.out.println("showFilmDetails filmId = " + filmId);

        Authentication auth = securityUtils.getUserAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("login", true);
        } else {
            model.addAttribute("login", false);
        }

        List<Store> storeList = storeService.fetchStores();
        model.addAttribute("storeList", storeList);

        Film film = filmServiceImpl.findFilm(filmId);
        if(film != null) {
            model.addAttribute("film", film);
            model.addAttribute("pageTitle", film.getTitle());
        }
        // status handling
        if(status != null) {
            model.addAttribute(status, true);
        }

        return "single";
    }

    @PostMapping("/films/film/{filmId}/rent")
    public String rentFilm(@PathVariable Long filmId, @RequestParam Long storeId, Model model){
        System.out.println("rentFilm() - RentFilm filmId = " + filmId + ", store = " + storeId);

        String status = filmServiceImpl.rentFilm(filmId, storeId);
        System.out.println("Status = " + status);

        return "redirect:/films/film/" + filmId + "?status=" + status;
    }
}
