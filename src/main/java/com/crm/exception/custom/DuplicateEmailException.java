package com.crm.exception.custom;

public class DuplicateEmailException extends RuntimeException{

    public DuplicateEmailException() {
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
