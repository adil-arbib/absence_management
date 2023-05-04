package com.ensah.eservice.exceptions.notfound;

public class NotFoundException extends Exception{

    private Class<?> entity;

    public NotFoundException() {
        super();
    }

    public NotFoundException(Class<?> entity) {
        this.entity = entity;
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> entity, String message) {
        super(message);
        this.entity = entity;
    }

    public Class<?> getEntity() {
        return entity;
    }


}
