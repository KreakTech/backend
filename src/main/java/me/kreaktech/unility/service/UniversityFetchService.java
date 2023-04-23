package me.kreaktech.unility.service;

import me.kreaktech.unility.constants.Enum;
import me.kreaktech.unility.entity.UniversityFetch;

public interface UniversityFetchService {
	UniversityFetch saveUniversityFetch(UniversityFetch universityFetch);
	UniversityFetch findUniversityFetchByNameAndLanguage(String name, Enum.Language language);

}
