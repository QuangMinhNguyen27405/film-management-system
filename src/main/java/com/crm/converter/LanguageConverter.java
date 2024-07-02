package com.crm.converter;

import com.crm.entity.Language;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LanguageConverter implements Converter<String, Language> {

    @Autowired
    public LanguageRepository languageRepository;

    @Override
    public Language convert(String languageId) {
        System.out.println("Trying to convert language with id " + languageId + " into a language");

        Long parseId = Long.parseLong(languageId);

        Optional<Language> dbLanguage = languageRepository.findById(parseId);
        if(dbLanguage.isEmpty()){
            throw new RecordNotFoundException();
        } else{
            System.out.println("Found language with name " + parseId);
            return dbLanguage.get();
        }
    }
}
