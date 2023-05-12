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
	public UniversityFetch getUniversityFetchById(Integer id) {
		return Utils.unwrap(universityFetchRepository.findById(id), id);
	}

	@Override
	public UniversityFetch saveUniversityFetch(UniversityFetch universityFetch) {
		return universityFetchRepository.save(universityFetch);
	}

	@Override
	public UniversityFetch getUniversityFetchByUniversityIdAndLanguage(Integer universityId, Enum.Language language) {
		Optional<UniversityFetch> optional = universityFetchRepository.findByUniversityIdAndLanguage(universityId, language);
		return Utils.unwrap(optional, universityId + " and language " + language);
	}

	@Override
	public void deleteUniversityFetchById(Integer id) {
		universityFetchRepository.deleteById(id);
    }

}
