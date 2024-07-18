package com.crm.converter;

import com.crm.repository.ActorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import com.crm.entity.Actor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class ActorConverter implements Converter<String, Actor> {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Actor convert(String actor) {
        log.info("Trying to convert actor name " + actor + " into an actor" );

        // process name into first name and last name
        String[] name = actor.split(" ");
        String firstName = name[0];
        String lastName = name[1];

        // check database
        Optional<Actor> actorFound = actorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (actorFound.isEmpty()){
            return new Actor(firstName, lastName);
        }
        log.info("Found actor with firstName " + firstName + " and lastName " + lastName);
        return actorFound.get();
    }
}
