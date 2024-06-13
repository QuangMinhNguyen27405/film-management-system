package com.crm.controller;

import com.crm.entity.Category;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    private List<Category> fetchCategory() {
        System.out.println("CategoryController - fetchCategory()");
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    private List<Category> createCategory(@RequestBody List<Category> categories) {
        System.out.println("CategoryController - createCategory()");
        return categoryRepository.saveAll(categories);
    }

    @PutMapping("/categories/{categoryId}")
    private Category updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        System.out.println("CategoryController - updateCategory()");
        Optional<Category> dbCategory = categoryRepository.findById(categoryId);
        if(dbCategory.isEmpty()){
            throw new RecordNotFoundException("Category with id " + categoryId + " does not exist.");
        }
        return categoryRepository.save(category);
    }

    @DeleteMapping("/categories")
    private String deleteCategory(@PathVariable Long categoryId){
        System.out.println("CategoryController - deleteCategory()");
        Optional<Category> dbCategory = categoryRepository.findById(categoryId);
        if(dbCategory.isEmpty()){
            throw new RecordNotFoundException("Category with id " + categoryId + " does not exist.");
        }
        categoryRepository.deleteById(categoryId);
        return "Category with id " + categoryId + " deleted.";
    }
}
