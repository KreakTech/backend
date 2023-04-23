package me.kreaktech.unility.service;

import me.kreaktech.unility.constants.Enum;
import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.repository.UniversityFetchRepository;
import me.kreaktech.unility.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityFetchServiceImpl implements UniversityFetchService {

	@Autowired
	private UniversityFetchRepository universityFetchRepository;


	@Override
	public UniversityFetch saveUniversityFetch(UniversityFetch universityFetch) {
		return universityFetchRepository.save(universityFetch);
	}

	@Override
	public UniversityFetch findUniversityFetchByNameAndLanguage(String name, Enum.Language language) {
		Optional<UniversityFetch> optional = universityFetchRepository.findByUniversityNameAndLanguage(name, language);
		return Utils.unwrap(optional, name + " and language " + language);
	}


}
