package me.kreaktech.unility.utils;

import java.util.Optional;

import me.kreaktech.unility.exception.EntityNotFoundException;

public class Utils {
	public static <T, V> T unwrap(Optional<T> entity, V identifier) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new EntityNotFoundException(identifier, entity.getClass());
	}
}
