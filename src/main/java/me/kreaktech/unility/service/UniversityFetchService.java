package me.kreaktech.unility.service;

import me.kreaktech.unility.constants.Enum;
import me.kreaktech.unility.entity.UniversityFetch;

public interface UniversityFetchService {

	UniversityFetch getUniversityFetchById(Integer id);

	UniversityFetch saveUniversityFetch(UniversityFetch universityFetch);

	UniversityFetch getUniversityFetchByUniversityNameAndLanguage(String universityName, Enum.Language language);

	void deleteUniversityFetchById(Integer id);
}