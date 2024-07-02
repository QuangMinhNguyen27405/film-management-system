package com.crm.converter;

import com.crm.entity.Category;
import com.crm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category convert(String categoryId) {
        System.out.println("Trying to convert String categoryId " + categoryId + " into category");

        Long parseId = Long.parseLong(categoryId);

        // check database
        Optional<Category> category = categoryRepository.findById(parseId);
        if(category.isEmpty()){
            System.out.println("Not found");
            return null;
        } else{
            System.out.println("Found category");
            return category.get();
        }
    }
}
