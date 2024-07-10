package com.crm.service.impl;

import com.crm.entity.Category;
import com.crm.entity.Customer;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> fetchCategory(){
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category findCategory(Long categoryId){
        Optional<Category> dbCategory = categoryRepository.findById(categoryId);
        if(dbCategory.isEmpty()){
            throw new RecordNotFoundException();
        }
        return dbCategory.get();
    }

    // Add new or modify category
    public Category changeCategory(Category category){
        return categoryRepository.save(category);
    }
}
