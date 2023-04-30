package me.kreaktech.unility.service;

import me.kreaktech.unility.constants.Enum;
import me.kreaktech.unility.entity.UniversityFetch;

public interface UniversityFetchService {

	UniversityFetch getUniversityFetch(Integer id);
	UniversityFetch saveUniversityFetch(UniversityFetch universityFetch);
	UniversityFetch getUniversityFetchByNameAndLanguage(String name, Enum.Language language);

}
