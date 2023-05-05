package me.kreaktech.unility.service;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.constants.Enum;
import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.entity.UniversityFetch;
import me.kreaktech.unility.repository.UniversityFetchRepository;
import me.kreaktech.unility.repository.UniversityRepository;
import me.kreaktech.unility.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private UniversityFetchRepository universityFetchRepository;

    @Override
    public University getUniversity(Integer id) {
        Optional<University> university = universityRepository.findById(id);
        return Utils.unwrap(university, id);
    }

    @Override
    public University createUniversity(University university) {
        University savedUniversity = universityRepository.save(university);

		for(Enum.Language language: Enum.Language.values()) {
			UniversityFetch universityFetch = new UniversityFetch();
			universityFetch.setUniversity(university);
			universityFetch.setCafeteriaLastFetchDate(new Timestamp(0));
			universityFetch.setAnnouncementsLastFetchMD5("");
			universityFetch.setLanguage(language);

			universityFetchRepository.save(universityFetch);
		}

		return savedUniversity;
    }

    @Override
    public void deleteUniversity(Integer id) {
        universityRepository.deleteById(id);
	}

    @Override
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

    @Override
    public University getUniversityByName(String name) {
        Optional<University> university = universityRepository.findByName(name);
        return Utils.unwrap(university, name);
    }

}
