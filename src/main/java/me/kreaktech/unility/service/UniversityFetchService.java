package me.kreaktech.unility.service;

import me.kreaktech.unility.constants.Enum;
import me.kreaktech.unility.entity.UniversityFetch;

import java.util.List;

public interface UniversityFetchService {

	UniversityFetch getUniversityFetchById(Integer id);

	UniversityFetch saveUniversityFetch(UniversityFetch universityFetch);

	UniversityFetch getUniversityFetchByUniversityIdAndLanguage(Integer universityId, Enum.Language language);

	List<UniversityFetch> findAllByUniversityId(Integer universityId);

	void deleteUniversityFetchById(Integer id);
}