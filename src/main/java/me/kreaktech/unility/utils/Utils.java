package me.kreaktech.unility.utils;

import java.util.Optional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import me.kreaktech.unility.exception.EntityNotFoundException;

public class Utils {
	public static <T, V> T unwrap(Optional<T> entity, V identifier) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new EntityNotFoundException(identifier, entity.getClass());
	}

	public static <T> T parseObjectToEntity(Object object, Class<T> clazz) {
		LinkedTreeMap<String, Object> data = (LinkedTreeMap<String, Object>) object;
		Gson gson = new Gson();
		String jsonData = gson.toJson(data);
		T objectifiedData = gson.fromJson(jsonData, clazz);
		return objectifiedData;
	}

	public static Timestamp stringToTimestamp(String str) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsedDate = dateFormat.parse(str);
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		return timestamp;
	}
}
