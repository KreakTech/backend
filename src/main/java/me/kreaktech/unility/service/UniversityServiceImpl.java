package me.kreaktech.unility.service;

import me.kreaktech.unility.entity.University;
import me.kreaktech.unility.exception.EntityNotFoundException;
import me.kreaktech.unility.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {

	private UniversityRepository universityRepository;

	@Override
	public University getUniversity(int id) {
		Optional<University> university = universityRepository.findById(id);
		return unwrapUniversity(university, id);
	}

	@Override
	public University createUniversity(University university) {
		return universityRepository.save(university);
	}

	@Override
	public void deleteUniversity(int id) {
		universityRepository.deleteById(id);
		return;
	}

	@Override
	public List<University> getUniversities() {
		return universityRepository.findAll();	
	}

	private static University unwrapUniversity(Optional<University> entity, int id) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new EntityNotFoundException(id, University.class);
	}


}
