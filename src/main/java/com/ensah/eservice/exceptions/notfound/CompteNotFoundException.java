package com.ensah.eservice.exceptions.notfound;

public class CompteNotFoundException extends NotFoundException{

    public CompteNotFoundException() {
        this("Compte Not Found");
    }

    public CompteNotFoundException(Class<?> entity) {
        super(entity);
    }

    public CompteNotFoundException(String message) {
        super(message);
    }

    public CompteNotFoundException(Class<?> entity, String message) {
        super(entity, message);
    }
}
