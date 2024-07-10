package com.crm.controller;

import com.crm.entity.*;
import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.CategoryRepository;
import com.crm.repository.LanguageRepository;
import com.crm.repository.RoleRepository;
import com.crm.service.impl.CategoryService;
import com.crm.service.impl.CustomerService;
import com.crm.service.impl.FilmService;
import com.crm.service.impl.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    // CUSTOMER ADMINISTRATIONS

    // Customers pagination default page 1
    @GetMapping("/customers/page")
    public String customersHomePage(Model model){
        return findPaginatedCustomer(1, model);
    }

    // Customers pagination display
    @GetMapping("/customers/page/{pageNo}")
    public String findPaginatedCustomer(@PathVariable (value = "pageNo") int pageNo, Model model){

        int pageSize = 4;

        Page<Customer> page = customerService.findPaginated(pageNo, pageSize);
        List<Customer> listCustomer = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("customers", listCustomer);

        return "admin_customers";
    }

    // Find customer
    @GetMapping("/customers/findCustomer/{customerId}")
    @ResponseBody
    public Customer findCustomer(@PathVariable Long customerId){
        System.out.println("AdminController - findOne()");
        try {
            return customerService.findCustomer(customerId);
        } catch (RecordNotFoundException ex) {
            return null;
        }
    }

    @GetMapping("/customers/new")
    public String showCreateCustomer(Model model){

        List<Role> roles = roleRepository.findAll();

        model.addAttribute("allRoles", roles);

        return "admin_customer_add";
    }

    // Update or save customer
    @PostMapping("/customers/new")
    public String doCreateCustomer(@ModelAttribute @Valid Customer customer, BindingResult result, Model model){
        System.out.println(("Admin Controller - saveCustomer()"));
        try {
            customerService.createCustomer(customer);
            return "redirect:/admin/customers/page/1";
        } catch (DuplicateEmailException ex){
            return "redirect:/admin/customers/page/1";
        } catch (RecordNotFoundException ex){
            return "redirect:/admin/customers/page/1";
        }
    }

    @GetMapping("/customers/update/{customerId}")
    public String showUpdateCustomer(@PathVariable Long customerId, Model model){

        List<Role> roles = roleRepository.findAll();
        Customer customer = customerService.findCustomer(customerId);

        model.addAttribute("customer", customer);
        model.addAttribute("allRoles", roles);

        return "admin_customer_edit";
    }

    @PostMapping("/customers/update")
    public String doUpdateCustomer(@ModelAttribute @Valid Customer customer, BindingResult result, Model model){
        System.out.println(("Admin Controller - saveCustomer()"));
        try {
            customerService.updateCustomer(customer);
            return "redirect:/admin/customers/page/1";
        } catch (DuplicateEmailException ex){
            return "redirect:/admin/customers/page/1";
        } catch (RecordNotFoundException ex){
            return "redirect:/admin/customers/page/1";
        }
    }

    // Deactivate Status Of Customer
    @GetMapping("customers/deactivate/{customerId}")
    public String deactivateCustomer(@PathVariable Long customerId){
        System.out.println(("Admin Controller - deactivateCustomer()"));
        try {
            customerService.deactivateCustomer(customerId);
            return "redirect:/admin/customers/page/1";
        } catch(RecordNotFoundException ex) {
            return "redirect:/admin/customers/page/1";
        }
    }

    // FILM ADMINISTRATIONS

    // Films pagination default page 1
    @GetMapping("/films/page")
    public String filmsHomePage(Model model){
        return findPaginatedFilm(1, model);
    }

    // Films pagination display
    @GetMapping("/films/page/{pageNo}")
    public String findPaginatedFilm(@PathVariable (value = "pageNo") int pageNo, Model model){

        int pageSize = 4;

        Page<Film> page = filmService.findPaginated(pageNo, pageSize);
        List<Film> filmList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("films", filmList);

        return "admin_films";
    }

    // Find Film
    @GetMapping("/films/findFilm/{filmId}")
    public Film findFilm(@PathVariable Long filmId){
        System.out.println("FilmControl - findFilm()");
        try{
            return filmService.findFilm(filmId);
        } catch (RecordNotFoundException ex){
            return null;
        }
    }

    // Add new film
    @GetMapping("/films/new")
    public String showCreateFilm(Model model){

        List<Category> categories = categoryRepository.findAll();
        List<Language> languages = languageRepository.findAll();

        model.addAttribute("allCategories", categories);
        model.addAttribute("allLanguages", languages);

        return "admin_film_add";
    }

    @PostMapping("/films/new")
    public String doCreateFilm(@ModelAttribute @Valid Film film,
                              Model model, BindingResult result){

        System.out.println("FilmControl - doCreateFilm()");
        try {
            filmService.createFilm(film);
            return "redirect:/admin/films/page";
        } catch (Exception ex){
            return "redirect:/admin/films/page";
        }
    }

    // Update film
    @GetMapping("/films/update/{filmId}")
    private String showUpdateFilm(@PathVariable Long filmId, Model model){

        Film film = filmService.findFilm(filmId);
        List<Category> categories = categoryRepository.findAll();
        List<Language> languages = languageRepository.findAll();

        model.addAttribute("allCategories", categories);
        model.addAttribute("allLanguages", languages);
        model.addAttribute("film", film);

        return "admin_film_edit";
    }

    @PostMapping("/films/update")
    public String doUpdateFilm(@ModelAttribute @Valid Film film,
                              Model model, BindingResult result){

        System.out.println("FilmControl - doUpdateFilm()");
        try {
            filmService.updateFilm(film);
            return "redirect:/admin/films/page";
        } catch (Exception ex){
            return "redirect:/admin/films/page";
        }
    }

    // STAFF ADMINISTRATIONS

    // Staffs pagination default page 1
    @GetMapping("/staffs/page")
    public String staffsHomePage(Model model){
        return findPaginatedStaff(1, model);
    }

    // Staffs pagination display
    @GetMapping("/staffs/page/{pageNo}")
    public String findPaginatedStaff(@PathVariable (value = "pageNo") int pageNo, Model model){

        int pageSize = 3;

        Page<Staff> page = staffService.findPaginated(pageNo, pageSize);
        List<Staff> staffList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("staffs", staffList);

        return "admin_staffs";
    }

}
