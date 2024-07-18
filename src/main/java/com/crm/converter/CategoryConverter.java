package com.crm.converter;

import com.crm.entity.Category;
import com.crm.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class CategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category convert(String categoryId) {
        log.info("Trying to convert String categoryId " + categoryId + " into category");

        Long parseId = Long.parseLong(categoryId);

        // check database
        Optional<Category> category = categoryRepository.findById(parseId);
        if(category.isEmpty()){
            log.info("Not found");
            return null;
        } else{
            log.info("Found category");
            return category.get();
        }
    }
}
