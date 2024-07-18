package com.crm.converter;

import com.crm.entity.Language;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.repository.LanguageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class LanguageConverter implements Converter<String, Language> {

    @Autowired
    public LanguageRepository languageRepository;

    @Override
    public Language convert(String languageId) {
        log.info("Trying to convert language with id " + languageId + " into a language");

        Long parseId = Long.parseLong(languageId);

        Optional<Language> dbLanguage = languageRepository.findById(parseId);
        if(dbLanguage.isEmpty()){
            throw new RecordNotFoundException();
        } else{
            log.info("Found language with name " + parseId);
            return dbLanguage.get();
        }
    }
}
