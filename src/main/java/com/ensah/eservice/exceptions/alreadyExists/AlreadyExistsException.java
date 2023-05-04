package com.ensah.eservice.exceptions.alreadyExists;

public class AlreadyExistsException extends Exception{

    private Class<?> entity;

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(Class<?> entity) {
        this.entity = entity;
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(Class<?> entity, String message) {
        super(message);
        this.entity = entity;
    }

    public Class<?> getEntity() {
        return entity;
    }
}
