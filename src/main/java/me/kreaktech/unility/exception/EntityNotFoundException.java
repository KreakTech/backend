package me.kreaktech.unility.exception;

public class EntityNotFoundException extends RuntimeException {

	public <T> EntityNotFoundException(T identifier, Class<?> entity) {
		super("The " + entity.getSimpleName().toLowerCase() + " with identifier '" + identifier + "' does not exist in our records");
	}

}