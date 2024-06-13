package com.crm.utils;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateUtils {

    public LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }
}
