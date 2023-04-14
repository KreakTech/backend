package me.kreaktech.unility.exception;

public class EntityNotFoundException extends RuntimeException { 

    public EntityNotFoundException(Integer id, Class<?> entity) {
            super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }
}